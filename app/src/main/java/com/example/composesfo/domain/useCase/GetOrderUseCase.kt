package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toOrderDetail
import com.example.composesfo.domain.model.OrderDetail
import com.example.composesfo.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(userId: String, orderId: String): Flow<Resource<OrderDetail>> = flow {
        try {
            emit(Resource.Loading<OrderDetail>())
            val orderDetail = repository.getOrderById(userId,orderId).toOrderDetail()
            emit(Resource.Success<OrderDetail>(orderDetail))
        } catch (e: HttpException) {
            emit(Resource.Error<OrderDetail>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<OrderDetail>( "Couldn't reach server. Check your internet connection"))
        }
    }
}