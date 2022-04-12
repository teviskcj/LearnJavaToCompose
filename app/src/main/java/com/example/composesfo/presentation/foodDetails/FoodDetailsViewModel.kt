package com.example.composesfo.presentation.foodDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Constants
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.useCase.GetFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val getFoodUseCase: GetFoodUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(FoodDetailsState())
    val state: State<FoodDetailsState> = _state

    var stateQuantity by mutableStateOf(0)
        private set

    init {
        stateQuantity = 1
        savedStateHandle.get<String>(Constants.PARAM_FOOD_ID)?.let { foodId ->
            getFood(foodId)
        }
    }

    private fun getFood(foodId: String) {
        getFoodUseCase(foodId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = FoodDetailsState(food = result.data)
                }
                is Resource.Error -> {
                    _state.value = FoodDetailsState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = FoodDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun increaseQuantity() {
        stateQuantity += 1
    }

    fun decreaseQuantity() {
        if (stateQuantity > 1)
            stateQuantity -= 1
    }
}