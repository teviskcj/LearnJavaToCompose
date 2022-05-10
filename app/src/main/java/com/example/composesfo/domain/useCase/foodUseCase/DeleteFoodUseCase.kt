package com.example.composesfo.domain.useCase.foodUseCase

import com.example.composesfo.common.Resource
import com.example.composesfo.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteFoodUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(foodId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            repository.deleteFood(foodId)
            emit(Resource.Success<String>(foodId))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<String>( "Couldn't reach server. Check your internet connection"))
        }
    }
}