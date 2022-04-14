package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.OrderDto
import com.example.composesfo.data.remote.dto.toOrder
import com.example.composesfo.domain.model.Order
import com.example.composesfo.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(userId: String, orderId: String, orderDto: OrderDto): Flow<Resource<Order>> = flow {
        try {
            emit(Resource.Loading<Order>())
            repository.createOrder(userId,orderId, orderDto)
            emit(Resource.Success<Order>(orderDto.toOrder()))
        } catch (e: HttpException) {
            emit(Resource.Error<Order>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<Order>( "Couldn't reach server. Check your internet connection"))
        }
    }
}