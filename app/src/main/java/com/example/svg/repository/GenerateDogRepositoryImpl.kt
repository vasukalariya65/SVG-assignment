package com.example.svg.repository

import com.example.svg.apis.RetrofitHelper
import com.example.svg.models.GenerateDogResponseModel
import retrofit2.Response
import retrofit2.Retrofit

class GenerateDogRepositoryImpl(private val retrofitHelper: RetrofitHelper):GenerateDogRepository {
    override suspend fun generateDogImage(): Response<GenerateDogResponseModel> {
        return retrofitHelper.api.generateDogImage()
    }

}