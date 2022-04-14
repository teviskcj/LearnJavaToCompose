package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Order

data class OrderDto(
    val address: String,
    val city: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val orderID: String,
    val phone: String,
    val state: String,
    val time: String,
    val totalAmount: String
)

fun OrderDto.toOrder(): Order {
    return Order(
        address = address,
        city = city,
        date = date,
        latitude = latitude,
        longitude = longitude,
        name = name,
        orderID = orderID,
        phone = phone,
        state = state,
        time = time,
        totalAmount = totalAmount,
    )
}