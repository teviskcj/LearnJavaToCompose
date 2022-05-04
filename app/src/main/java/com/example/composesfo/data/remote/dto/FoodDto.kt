package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Food

data class FoodDto(
    val id: String,
    val food_description: String,
    val food_image_url: String,
    val food_name: String,
    val food_price: String
)

/*
data class FoodDto(
    val foodCategory: String,
    val foodDescription: String,
    val foodImage: String,
    val foodName: String,
    val foodPopular: String,
    val foodPrice: String
)*/

fun FoodDto.toFood(): Food {
    return Food(
        id = id,
        food_description = food_description,
        food_image_url = food_image_url,
        food_name = food_name,
        food_price = food_price
    )
}
