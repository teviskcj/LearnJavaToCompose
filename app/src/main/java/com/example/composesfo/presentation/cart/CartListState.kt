package com.example.composesfo.presentation.cart

import com.example.composesfo.domain.model.Cart

data class CartListState(
    val isLoading: Boolean = false,
    val cartList: List<Cart> = emptyList(),
    val error: String = ""
)
