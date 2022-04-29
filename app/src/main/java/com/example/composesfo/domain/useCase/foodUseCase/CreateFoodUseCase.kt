package com.example.composesfo.domain.useCase.foodUseCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CreateFoodUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(foodId: String, foodDto: FoodDto): Flow<Resource<FoodDto>> = flow {
        try {
            emit(Resource.Loading<FoodDto>())
            repository.createFood(foodId, foodDto)
            emit(Resource.Success<FoodDto>(foodDto))
        } catch (e: HttpException) {
            emit(Resource.Error<FoodDto>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<FoodDto>( "Couldn't reach server. Check your internet connection"))
        }
    }
}