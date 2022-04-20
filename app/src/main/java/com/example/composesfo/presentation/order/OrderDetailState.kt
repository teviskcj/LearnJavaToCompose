package com.example.composesfo.presentation.order

import com.example.composesfo.domain.model.OrderView

data class OrderDetailState(
    val isLoading: Boolean = false,
    val orderView: List<OrderView> = emptyList(),
    val error: String = ""
)