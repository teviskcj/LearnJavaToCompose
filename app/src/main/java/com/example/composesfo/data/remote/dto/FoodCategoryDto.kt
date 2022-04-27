package com.example.composesfo.data.remote.dto

data class FoodCategoryDto(
    val category: String,
    val category_description: String,
    val foods: List<String>
)