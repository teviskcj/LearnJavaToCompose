package com.example.composesfo.presentation.login

import com.example.composesfo.domain.model.UserLogin

data class LoginState(
    val isLoading: Boolean = false,
    val users: List<UserLogin> = emptyList(),
    val error: String = ""
)
