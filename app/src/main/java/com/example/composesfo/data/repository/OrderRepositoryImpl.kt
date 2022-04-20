package com.example.composesfo.data.repository

import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.remote.dto.OrderDto
import com.example.composesfo.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: SFOApi
) : OrderRepository {
    override suspend fun createOrder(userId: String, orderId: String, orderDto: OrderDto) {
        return api.createOrder(userId, orderId, orderDto)
    }

    override suspend fun getOrders(userId: String): Map<String, OrderDto> {
        return api.getOrders(userId)
    }

    override suspend fun getOrderById(userId: String, orderId: String): OrderDto {
        return api.getOrderById(userId, orderId)
    }
}