package com.example.composesfo.data.remote.dto

data class FoodCategoryDto(
    val id: String,
    val category_name: String,
    val category_description: String,
    val foods: List<String>
)