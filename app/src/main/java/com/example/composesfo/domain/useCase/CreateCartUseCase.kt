package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.data.remote.dto.toCart
import com.example.composesfo.domain.model.Cart
import com.example.composesfo.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(userId: String, foodId: String, cartDto: CartDto): Flow<Resource<Cart>> = flow {
        try {
            emit(Resource.Loading<Cart>())
            repository.createCart(userId,foodId, cartDto)
            emit(Resource.Success<Cart>(cartDto.toCart()))
        } catch (e: HttpException) {
            emit(Resource.Error<Cart>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<Cart>( "Couldn't reach server. Check your internet connection"))
        }
    }
}