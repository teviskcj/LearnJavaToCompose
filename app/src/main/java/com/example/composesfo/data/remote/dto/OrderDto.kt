package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Order
import com.example.composesfo.domain.model.OrderDetail
import com.example.composesfo.domain.model.OrderView

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

fun OrderDto.toOrderView(): OrderView {
    return OrderView(
        date = date,
        orderID = orderID,
        state = state,
        time = time,
        totalAmount = totalAmount
    )
}

fun OrderDto.toOrderDetail(): OrderDetail {
    return OrderDetail(
        address = address,
        city = city,
        date = date,
        name = name,
        orderID = orderID,
        phone = phone,
        state = state,
        time = time,
        totalAmount = totalAmount,
    )
}