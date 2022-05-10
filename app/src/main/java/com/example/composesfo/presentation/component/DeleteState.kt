package com.example.composesfo.presentation.component

data class DeleteState(
    val isLoading: Boolean = false,
    val id: String? = "",
    val error: String = ""
)
