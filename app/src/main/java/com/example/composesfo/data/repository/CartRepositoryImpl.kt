package com.example.composesfo.data.repository

import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: SFOApi
) : CartRepository {
    override suspend fun createCart(userId: String, foodId: String, cartDto: CartDto) {
        return api.createCart(userId, foodId, cartDto)
    }

    override suspend fun getCartList(userId: String): Map<String, CartDto> {
        return api.getCartList(userId)
    }
}