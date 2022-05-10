package com.example.composesfo.domain.useCase.foodUseCase

import javax.inject.Inject

data class FoodUseCase @Inject constructor(
    val getFoodsUseCase: GetFoodsUseCase,
    val getFoodUseCase: GetFoodUseCase,
    val createFoodUseCase: CreateFoodUseCase,
    val deleteFoodUseCase: DeleteFoodUseCase
)
