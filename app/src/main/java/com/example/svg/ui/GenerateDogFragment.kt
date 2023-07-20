package com.example.svg.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.svg.R
import com.example.svg.apis.RetrofitHelper
import com.example.svg.databinding.FragmentGenerateDogBinding
import com.example.svg.repository.GenerateDogRepositoryImpl
import com.example.svg.viewmodel.GenerateDogViewModel
import com.example.svg.viewmodel.GenerateDogViewModelFactory
import org.koin.android.ext.android.get

class GenerateDogFragment : Fragment() {

    private lateinit var mBinding: FragmentGenerateDogBinding
    private lateinit var viewModel: GenerateDogViewModel
    private lateinit var viewModelProviderFactory: GenerateDogViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_generate_dog, container, false)
        viewModelProviderFactory = GenerateDogViewModelFactory(get())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[GenerateDogViewModel::class.java]

        mBinding.btnGenerateImage.setOnClickListener {
            viewModel.generateDogImage()
        }
        viewModel.getGeneratedDogList().observe(viewLifecycleOwner) {
            if (!it.first.isEmpty() && it.second.isEmpty() && viewModel.getIsApiCalled())
                Glide.with(requireContext()).load(it.first.first.message)
                    .into(mBinding.ivGeneratedImage)
            else if (it.second.isNotEmpty())
                Toast.makeText(requireContext(), it.second, Toast.LENGTH_SHORT).show()
        }
        return mBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setIsApiCalled(false)
    }

    companion object {
        fun newInstance() = GenerateDogFragment()
    }
}