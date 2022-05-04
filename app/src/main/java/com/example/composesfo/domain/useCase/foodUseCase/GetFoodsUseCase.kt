package com.example.composesfo.domain.useCase.foodUseCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toFood
import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFoodsUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(): Flow<Resource<List<Food>>> = flow {
        try {
            emit(Resource.Loading<List<Food>>())
            val list = repository.getFoods().values
            val foods = list.map { it.toFood() }
            emit(Resource.Success<List<Food>>(foods))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Food>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<List<Food>>( "Couldn't reach server. Check your internet connection"))
        }
    }
}