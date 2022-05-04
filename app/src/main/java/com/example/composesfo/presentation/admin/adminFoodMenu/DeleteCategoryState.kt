package com.example.composesfo.presentation.admin.adminFoodMenu

data class DeleteCategoryState(
    val isLoading: Boolean = false,
    val categoryId: String? = "",
    val error: String = ""
)
