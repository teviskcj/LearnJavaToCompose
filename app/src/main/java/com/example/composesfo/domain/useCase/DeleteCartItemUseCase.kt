package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(userId: String, foodId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            repository.deleteCartItem(userId, foodId)
            emit(Resource.Success<String>(userId))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<String>( "Couldn't reach server. Check your internet connection"))
        }
    }
}