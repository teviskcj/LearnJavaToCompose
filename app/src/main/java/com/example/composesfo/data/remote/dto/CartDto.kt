package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Cart

data class CartDto(
    val foodName: String,
    val foodPrice: String,
    val quantity: String
)

fun CartDto.toCart(): Cart {
    return Cart(
        foodName = foodName,
        foodPrice = foodPrice,
        quantity = quantity
    )
}