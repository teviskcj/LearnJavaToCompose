package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.model.UserLogin
import com.example.composesfo.domain.model.UserRegister

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

fun UserDto.toUserRegister(): UserRegister {
    return UserRegister(
        name = name,
        password = password,
        phone = phone,
    )
}