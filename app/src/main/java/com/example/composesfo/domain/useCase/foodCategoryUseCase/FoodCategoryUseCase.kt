package com.example.composesfo.domain.useCase.foodCategoryUseCase

import javax.inject.Inject

data class FoodCategoryUseCase @Inject constructor(
    val createFoodCategoryUseCase: CreateFoodCategoryUseCase,
    val getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
    val getFoodCategoryUseCase: GetFoodCategoryUseCase,
    val deleteFoodCategoryUseCase: DeleteFoodCategoryUseCase
)
