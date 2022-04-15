package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.CartDto

interface CartRepository {
    suspend fun createCart(userId: String, foodId: String, cartDto: CartDto)

    suspend fun getFoodFromCart(userId: String, foodId: String): CartDto

    suspend fun getCartList(userId: String): Map<String, CartDto>

    suspend fun createCartOrder(userId: String, orderId: String, foodId: String, cartDto: CartDto)

    suspend fun deleteCartList(userId: String)
}