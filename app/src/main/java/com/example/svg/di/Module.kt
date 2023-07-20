package com.example.svg.di

import com.example.svg.apis.RetrofitHelper
import com.example.svg.repository.GenerateDogRepository
import com.example.svg.repository.GenerateDogRepositoryImpl
import com.example.svg.viewmodel.GenerateDogViewModelFactory
import org.koin.dsl.module

val appModule = module {

    single { RetrofitHelper() }

    single<GenerateDogRepository> { GenerateDogRepositoryImpl(get()) }

    single { GenerateDogViewModelFactory(get()) }
}