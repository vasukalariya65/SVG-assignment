package com.example.svg.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.svg.R
import com.example.svg.apis.RetrofitHelper
import com.example.svg.databinding.FragmentBaseBinding
import com.example.svg.models.GenerateDogResponseModel
import com.example.svg.repository.GenerateDogRepositoryImpl
import com.example.svg.utils.Constants
import com.example.svg.utils.Constants.DOG_CACHE_VALUE
import com.example.svg.utils.Constants.SHARE_PREF_DOG_CACHE
import com.example.svg.viewmodel.GenerateDogViewModel
import com.example.svg.viewmodel.GenerateDogViewModelFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.LinkedList
import org.koin.android.ext.android.get

class BaseFragment : Fragment() {

    private lateinit var mBinding: FragmentBaseBinding
    private lateinit var viewModel: GenerateDogViewModel
    private lateinit var viewModelProviderFactory: GenerateDogViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_base, container, false)
        viewModelProviderFactory = GenerateDogViewModelFactory(get())
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory)[GenerateDogViewModel::class.java]

        var gson = Gson()
        val pref = requireActivity().getSharedPreferences(
            SHARE_PREF_DOG_CACHE,
            MODE_PRIVATE
        )
        var jsonText = pref.getString(DOG_CACHE_VALUE, Constants.EMPTY_STRING)
        val bookType = object : TypeToken<LinkedList<GenerateDogResponseModel>>() {}.type
        val books = Gson().fromJson<LinkedList<GenerateDogResponseModel>>(jsonText, bookType)
        if (!books.isNullOrEmpty() && viewModel.getIsCacheData()) {
            viewModel.handleCacheResponse(books)
            pref.edit().clear().apply()
        }
        viewModel.setIsCacheData(false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.apply {
            btnGenerateDog.setOnClickListener {
                Navigation.findNavController(mBinding.root).navigate(R.id.action_baseFragment_to_generateDogFragment)
            }

            btnRecentGenerate.setOnClickListener {
                Navigation.findNavController(mBinding.root).navigate(R.id.action_baseFragment_to_recentGeneratedFragment)
            }
        }
    }

    companion object {

        fun newInstance() = BaseFragment()
    }
}