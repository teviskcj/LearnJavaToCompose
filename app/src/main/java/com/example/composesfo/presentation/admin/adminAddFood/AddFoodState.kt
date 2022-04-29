package com.example.composesfo.presentation.admin.adminAddFood

import com.example.composesfo.data.remote.dto.FoodDto

data class AddFoodState(
    val isLoading: Boolean = false,
    val foodDto: FoodDto? = null,
    val error: String = ""
)
