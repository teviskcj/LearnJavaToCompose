package com.example.composesfo.domain.useCase.foodCategoryUseCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.repository.FoodCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFoodCategoryUseCase @Inject constructor(
    private val repository: FoodCategoryRepository
) {
    operator fun invoke(categoryId: String): Flow<Resource<FoodCategoryDto>> = flow {
        try {
            emit(Resource.Loading<FoodCategoryDto>())
            val category = repository.getCategoryById(categoryId)
            emit(Resource.Success<FoodCategoryDto>(category))
        } catch (e: HttpException) {
            emit(
                Resource.Error<FoodCategoryDto>(e.localizedMessage ?: "An unexpected error occurred")
            )
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<FoodCategoryDto>("Couldn't reach server. Check your internet connection"))
        }
    }
}