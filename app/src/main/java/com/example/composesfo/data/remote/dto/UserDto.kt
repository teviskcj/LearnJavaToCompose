package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.model.UserLogin

data class UserDto(
    val name: String,
    val password: String,
    val phone: String
)

fun UserDto.toUserLogin(): UserLogin {
    return UserLogin(
        password = password,
        phone = phone,
    )
}