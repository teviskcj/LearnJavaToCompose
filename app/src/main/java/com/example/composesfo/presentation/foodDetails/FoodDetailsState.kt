package com.example.composesfo.presentation.foodDetails

import com.example.composesfo.domain.model.Food

data class FoodDetailsState(
    val isLoading: Boolean = false,
    val food: Food? = null,
    val error: String = ""
)
