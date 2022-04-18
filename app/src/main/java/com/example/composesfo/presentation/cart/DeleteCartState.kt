package com.example.composesfo.presentation.cart

data class DeleteCartState (
    val isLoading: Boolean = false,
    val userId: String? = "",
    val error: String = ""
)