package com.example.svg.apis

import com.example.svg.models.GenerateDogResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface GenerateDogService {
    @GET("api/breeds/image/random")
    suspend fun generateDogImage(): Response<GenerateDogResponseModel>
}