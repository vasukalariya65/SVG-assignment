package com.example.svg.repository

import com.example.svg.models.GenerateDogResponseModel
import retrofit2.Response
import retrofit2.Retrofit

interface GenerateDogRepository {
    suspend fun generateDogImage(): Response<GenerateDogResponseModel>
}