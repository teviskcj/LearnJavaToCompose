package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.FoodDto

interface FoodRepository {

    suspend fun getFoods(): Map<String, FoodDto>

    suspend fun getFoodById(foodId: String): FoodDto

    suspend fun createFood(foodId: String, foodDto: FoodDto)
}