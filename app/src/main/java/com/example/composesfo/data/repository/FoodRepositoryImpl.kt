package com.example.composesfo.data.repository

import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val api: SFOApi
) : FoodRepository {
    override suspend fun getFoods(): Map<String, FoodDto> {
        return api.getFoods()
    }

    override suspend fun getFoodById(foodId: String): FoodDto {
        return api.getFoodById(foodId)
    }
}