package com.example.composesfo.presentation.cart

import com.example.composesfo.domain.model.Cart

data class DeleteCartState (
    val isLoading: Boolean = false,
    val userId: String? = "",
    val error: String = ""
)