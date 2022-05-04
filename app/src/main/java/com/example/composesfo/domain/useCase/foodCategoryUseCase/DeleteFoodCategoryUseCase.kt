package com.example.composesfo.domain.useCase.foodCategoryUseCase

import com.example.composesfo.common.Resource
import com.example.composesfo.domain.repository.FoodCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteFoodCategoryUseCase @Inject constructor(
    private val repository: FoodCategoryRepository
) {
    operator fun invoke(categoryId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            repository.deleteCategory(categoryId)
            emit(Resource.Success<String>(categoryId))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<String>( "Couldn't reach server. Check your internet connection"))
        }
    }
}