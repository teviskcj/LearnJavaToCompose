package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.FoodDto

interface FoodRepository {

    suspend fun getFoods(): List<FoodDto>

    suspend fun getFoodById(foodId: String): FoodDto
}