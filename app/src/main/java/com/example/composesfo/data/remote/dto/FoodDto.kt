package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Food

data class FoodDto(
    val foodCategory: String,
    val foodDescription: String,
    val foodImage: String,
    val foodName: String,
    val foodPopular: String,
    val foodPrice: String
)

fun FoodDto.toFood(): Food {
    return Food(
        foodCategory = foodCategory,
        foodDescription = foodDescription,
        foodImage = foodImage,
        foodName = foodName,
        foodPrice = foodPrice,
        foodPopular = foodPopular
    )
}