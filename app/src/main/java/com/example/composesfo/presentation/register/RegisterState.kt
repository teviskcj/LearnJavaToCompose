package com.example.composesfo.presentation.register

import com.example.composesfo.domain.model.UserRegister

data class RegisterState(
    val isLoading: Boolean = false,
    val userRegister: UserRegister? = null,
    val error: String = ""
)
