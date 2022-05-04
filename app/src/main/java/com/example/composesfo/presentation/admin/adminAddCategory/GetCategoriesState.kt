package com.example.composesfo.presentation.admin.adminAddCategory

import com.example.composesfo.data.remote.dto.FoodCategoryDto

data class GetCategoriesState(
    val isLoading: Boolean = false,
    val categoryList: List<FoodCategoryDto> = emptyList(),
    val error: String = ""
)