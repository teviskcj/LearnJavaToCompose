package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.CartDto

interface CartRepository {
    suspend fun createCart(userId: String, foodId: String, cartDto: CartDto)

    suspend fun getCartList(userId: String): Map<String, CartDto>
}