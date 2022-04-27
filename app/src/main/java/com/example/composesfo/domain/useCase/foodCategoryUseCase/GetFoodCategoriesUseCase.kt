package com.example.composesfo.domain.useCase.foodCategoryUseCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.repository.FoodCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFoodCategoriesUseCase @Inject constructor(
    private val repository: FoodCategoryRepository
) {
    operator fun invoke(): Flow<Resource<List<FoodCategoryDto>>> = flow {
        try {
            emit(Resource.Loading<List<FoodCategoryDto>>())
            val list = repository.getCategory().values
            emit(Resource.Success<List<FoodCategoryDto>>(list.toList()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<FoodCategoryDto>>(e.localizedMessage ?: "An unexpected error occurred")
            )
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<List<FoodCategoryDto>>("Couldn't reach server. Check your internet connection"))
        }
    }
}