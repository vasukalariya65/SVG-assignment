package com.example.svg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svg.repository.GenerateDogRepository

class GenerateDogViewModelFactory(private val generateDogRepository: GenerateDogRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GenerateDogViewModel(generateDogRepository) as T
    }
}