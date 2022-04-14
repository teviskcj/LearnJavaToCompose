package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.OrderDto

interface OrderRepository {

    suspend fun createOrder(userId: String, orderId: String, orderDto: OrderDto)
}