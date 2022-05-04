package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.FoodCategoryDto

interface FoodCategoryRepository {
    suspend fun createCategory(categoryId: String, foodCategoryDto: FoodCategoryDto)

    suspend fun getCategoryList(): Map<String, FoodCategoryDto>

    suspend fun getCategoryById(categoryId: String): FoodCategoryDto

    suspend fun deleteCategory(categoryId: String)

    //suspend fun getCategory(onCallListener: CallHelper.CallBack<Map<String, FoodCategoryDto>>)
}