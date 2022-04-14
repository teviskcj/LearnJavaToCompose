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
}