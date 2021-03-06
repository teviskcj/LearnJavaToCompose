package com.example.composesfo.presentation.admin.adminAddCategory

import com.example.composesfo.data.remote.dto.FoodCategoryDto

data class GetCategoryState(
    val isLoading: Boolean = false,
    val category: FoodCategoryDto? = null,
    val error: String = ""
)
