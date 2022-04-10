package com.example.composesfo.domain.useCase.getFood

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toFood
import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFoodUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(foodId: String): Flow<Resource<Food>> = flow {
        try {
            emit(Resource.Loading<Food>())
            val food = repository.getFoodById(foodId).toFood()
            emit(Resource.Success<Food>(food))
        } catch (e: HttpException) {
            emit(Resource.Error<Food>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<Food>( "Couldn't reach server. Check your internet connection"))
        }
    }

}