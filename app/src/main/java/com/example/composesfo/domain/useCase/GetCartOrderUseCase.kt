package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toCart
import com.example.composesfo.domain.model.Cart
import com.example.composesfo.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCartOrderUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(userId: String, orderId: String): Flow<Resource<List<Cart>>> = flow {
        try {
            emit(Resource.Loading<List<Cart>>())
            val list = repository.getCartListFromOrder(userId, orderId).values
            val cartList = list.map { it.toCart() }
            emit(Resource.Success<List<Cart>>(cartList))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Cart>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<List<Cart>>( "Couldn't reach server. Check your internet connection"))
        }
    }
}