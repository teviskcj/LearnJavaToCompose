package com.example.composesfo.data.remote

import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SFOApi {

    @GET("/Users/.json")
    suspend fun getUsers(): Map<String, UserDto>

    @GET("/Foods/.json")
    suspend fun getFoods(): Map<String, FoodDto>

    @GET("/Foods/{foodId}.json")
    suspend fun getFoodById(@Path("foodId") foodId: String): FoodDto
}