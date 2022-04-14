package com.example.composesfo.presentation.cart

import com.example.composesfo.domain.model.Cart

data class CartState(
    val isLoading: Boolean = false,
    val cart: Cart? = null,
    val error: String = ""
)
