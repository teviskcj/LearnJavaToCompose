package com.example.composesfo.presentation.payment

import com.example.composesfo.domain.model.Order

data class OrderState(
    val isLoading: Boolean = false,
    val order: Order? = null,
    val error: String = ""
)
