package com.example.composesfo.presentation.admin.adminEditFood

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Constants
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.domain.useCase.foodUseCase.FoodUseCase
import com.example.composesfo.presentation.admin.adminAddFood.AddFoodState
import com.example.composesfo.presentation.foodDetails.FoodDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminEditFoodViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(FoodDetailsState())
    val state: State<FoodDetailsState> = _state

    private val _stateEdit = mutableStateOf(AddFoodState())
    val stateAdd: State<AddFoodState> = _stateEdit

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var showGallery by mutableStateOf(false)

    init {
        savedStateHandle.get<String>(Constants.PARAM_FOOD_ID)?.let { foodId ->
            getFood(foodId)
        }
    }

    private fun getFood(foodId: String) {
        foodUseCase.getFoodUseCase(foodId).onEach { result ->
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

    fun editFood(foodId: String, foodDto: FoodDto) {
        foodUseCase.createFoodUseCase(foodId, foodDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateEdit.value = AddFoodState(foodDto = result.data)
                }
                is Resource.Error -> {
                    _stateEdit.value = AddFoodState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateEdit.value = AddFoodState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onNameChange(newString: String) {
        name = newString
    }

    fun onDescriptionChange(newString: String) {
        description = newString
    }

    fun onPriceChange(newString: String) {
        price = newString
    }

    fun onShowGalleryChange(boolean: Boolean) {
        showGallery = boolean
    }
}