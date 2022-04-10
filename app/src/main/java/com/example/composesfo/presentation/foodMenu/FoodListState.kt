package com.example.composesfo.presentation.foodMenu

import com.example.composesfo.domain.model.Food

data class FoodListState(
    val isLoading: Boolean = false,
    val foods: List<Food> = emptyList(),
    val error: String = ""
)
