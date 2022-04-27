package com.example.composesfo.presentation.admin.adminAddCategory

import com.example.composesfo.data.remote.dto.FoodCategoryDto

data class AddCategoryState(
    val isLoading: Boolean = false,
    val foodCategoryDto: FoodCategoryDto? = null,
    val error: String = ""
)
