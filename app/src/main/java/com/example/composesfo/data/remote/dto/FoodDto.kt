package com.example.composesfo.data.remote.dto

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
}*/
