package com.example.composesfo.domain.model

data class OrderDetail(
    val address: String,
    val city: String,
    val date: String,
    val name: String,
    val orderID: String,
    val phone: String,
    val state: String,
    val time: String,
    val totalAmount: String
)
