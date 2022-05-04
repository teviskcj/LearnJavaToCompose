package com.example.composesfo.data.repository

import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.repository.FoodCategoryRepository
import javax.inject.Inject

class FoodCategoryRepositoryImpl @Inject constructor(
    private val api: SFOApi
) : FoodCategoryRepository {
    override suspend fun createCategory(categoryId: String, foodCategoryDto: FoodCategoryDto) {
        return api.createCategory(categoryId, foodCategoryDto)
    }

    override suspend fun getCategoryList(): Map<String, FoodCategoryDto> {
        return api.getCategoryList()
    }

    override suspend fun getCategoryById(categoryId: String): FoodCategoryDto {
        return api.getCategoryById(categoryId)
    }

    override suspend fun deleteCategory(categoryId: String) {
        return api.deleteFoodCategory(categoryId)
    }

    /*override suspend fun getCategory(onCallListener: CallHelper.CallBack<Map<String, FoodCategoryDto>>) {
        CallHelper(onCallListener).call(api.getCategory())
        //return api.getCategory()
    }*/
}