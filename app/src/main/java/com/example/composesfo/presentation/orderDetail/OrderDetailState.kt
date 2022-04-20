package com.example.composesfo.presentation.orderDetail

import com.example.composesfo.domain.model.OrderDetail

data class OrderDetailState(
    val isLoading: Boolean = false,
    val orderDetail: OrderDetail? = null,
    val error: String = ""
)