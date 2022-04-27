package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.FoodCategoryDto

interface FoodCategoryRepository {
    suspend fun createCategory(categoryId: String, foodCategoryDto: FoodCategoryDto)

    suspend fun getCategory(): Map<String, FoodCategoryDto>
}