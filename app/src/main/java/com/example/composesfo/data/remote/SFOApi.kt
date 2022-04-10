package com.example.composesfo.data.remote

import com.example.composesfo.data.remote.dto.FoodDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SFOApi {

    @GET("/Foods")
    suspend fun getFoods(): List<FoodDto>

    @GET("/Foods/{foodId}")
    suspend fun getFoodById(@Path("foodId") foodId: String): FoodDto
}