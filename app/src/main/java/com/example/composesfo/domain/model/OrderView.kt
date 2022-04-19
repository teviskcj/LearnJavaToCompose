package com.example.composesfo.domain.model

data class OrderView(
    val date: String,
    val orderID: String,
    val state: String,
    val time: String,
    val totalAmount: String
)