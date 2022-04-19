package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toOrderView
import com.example.composesfo.domain.model.OrderView
import com.example.composesfo.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(userId: String): Flow<Resource<List<OrderView>>> = flow {
        try {
            emit(Resource.Loading<List<OrderView>>())
            val list = repository.getOrders(userId).values
            val orderList = list.map { it.toOrderView() }
            emit(Resource.Success<List<OrderView>>(orderList))
        } catch (e: HttpException) {
            emit(Resource.Error<List<OrderView>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<List<OrderView>>( "Couldn't reach server. Check your internet connection"))
        }
    }
}