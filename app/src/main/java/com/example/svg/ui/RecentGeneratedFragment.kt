package com.example.svg.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.svg.R
import com.example.svg.adapter.GeneratedDogAdapter
import com.example.svg.databinding.FragmentRecentGeneratedBinding
import com.example.svg.utils.Constants
import com.example.svg.viewmodel.GenerateDogViewModel
import com.example.svg.viewmodel.GenerateDogViewModelFactory
import com.google.gson.Gson
import java.util.LinkedList
import org.koin.android.ext.android.get

class RecentGeneratedFragment : Fragment() {
    private lateinit var mBinding: FragmentRecentGeneratedBinding
    private lateinit var viewModel: GenerateDogViewModel
    private lateinit var viewModelProviderFactory: GenerateDogViewModelFactory
    private lateinit var rvAdapter: GeneratedDogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_recent_generated, container, false)
        viewModelProviderFactory = GenerateDogViewModelFactory(get())
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory)[GenerateDogViewModel::class.java]

        rvAdapter = GeneratedDogAdapter(viewModel.getGeneratedDogList().value?.first ?: LinkedList())
        mBinding.rvRecentlyGenerated.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        mBinding.btnClearCache.setOnClickListener {
            viewModel.handleClearCache()
        }
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGeneratedDogList().observe(this) {
            if (viewModel.getIsDataCleared()) {
                rvAdapter.updateList(it.first)
                viewModel.setIsDataCleared(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val gson = Gson()
        val pref = requireActivity().getSharedPreferences(
            Constants.SHARE_PREF_DOG_CACHE,
            Context.MODE_PRIVATE
        )
        var list = gson.toJson(viewModel.getGeneratedDogList().value?.first)
        pref.edit().putString(Constants.DOG_CACHE_VALUE, list).apply()
    }

    companion object {
        fun newInstance() = RecentGeneratedFragment()
    }
}