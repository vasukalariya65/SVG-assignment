package com.example.svg.viewmodel


import android.util.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svg.models.GenerateDogResponseModel
import com.example.svg.repository.GenerateDogRepository
import com.example.svg.utils.Constants
import com.example.svg.utils.Constants.NUMBER_ZERO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.LinkedList

class GenerateDogViewModel(private val generateDogRepository: GenerateDogRepository) : ViewModel() {

    private val generatedDogList: MutableLiveData<Pair<LinkedList<GenerateDogResponseModel>, String>> =
        MutableLiveData()

    fun getGeneratedDogList(): LiveData<Pair<LinkedList<GenerateDogResponseModel>, String>> =
        generatedDogList

    private val lruCache: LruCache<String, GenerateDogResponseModel> = LruCache(20)

    private var isDataCleared = false
    private var isCacheData = true
    private var isApiCalled = false

    fun generateDogImage() = viewModelScope.launch(Dispatchers.IO) {
        val response = generateDogRepository.generateDogImage()
        handleGenerateDogResponse(response)
    }

    private fun handleGenerateDogResponse(response: Response<GenerateDogResponseModel>) {
        if (response.isSuccessful) {
            response.body()?.let {
                addGeneratedDogToList(it)
                isApiCalled = true
                return
            }
        }
        generatedDogList.postValue(Pair(LinkedList(), response.message()))
    }

    private fun addGeneratedDogToList(dogItem: GenerateDogResponseModel) {
        lruCache.put(dogItem.message, dogItem)
        val tempList = LinkedList<GenerateDogResponseModel>()
        val snapshot: Map<String, GenerateDogResponseModel> = lruCache.snapshot()
        for (id in snapshot.keys) {
            tempList.add(NUMBER_ZERO, lruCache[id])
        }
        tempList.let {
            generatedDogList.postValue(Pair(tempList, Constants.EMPTY_STRING))
        }
    }

    fun handleCacheResponse(cacheList: LinkedList<GenerateDogResponseModel>) {
        val list: LinkedList<GenerateDogResponseModel> = LinkedList()
        if (lruCache.size() > NUMBER_ZERO)
            lruCache.evictAll()
        for (i in cacheList.reversed()) {
            list.add(NUMBER_ZERO, i)
            lruCache.put(i.message, i)
        }
        generatedDogList.postValue(Pair(list, Constants.EMPTY_STRING))
    }

    fun getIsDataCleared(): Boolean {
        return isDataCleared
    }

    fun setIsDataCleared(value: Boolean) {
        isDataCleared = value
    }

    fun getIsCacheData(): Boolean {
        return isCacheData
    }

    fun setIsCacheData(value: Boolean) {
        isCacheData = value
    }

    fun getIsApiCalled(): Boolean {
        return isApiCalled
    }

    fun setIsApiCalled(value: Boolean) {
        isApiCalled = value
    }

    fun handleClearCache() {
        isDataCleared = true
        generatedDogList.postValue(Pair(LinkedList(), Constants.EMPTY_STRING))
        lruCache.evictAll()
    }
}