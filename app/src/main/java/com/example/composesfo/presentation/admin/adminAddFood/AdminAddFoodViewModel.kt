package com.example.composesfo.presentation.admin.adminAddFood

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.domain.useCase.foodUseCase.FoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminAddFoodViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AddFoodState())
    val state: State<AddFoodState> = _state

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var showGallery by mutableStateOf(false)

    fun createFood(foodId: String, foodDto: FoodDto) {
        foodUseCase.createFoodUseCase(foodId, foodDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = AddFoodState(foodDto = result.data)
                }
                is Resource.Error -> {
                    _state.value = AddFoodState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = AddFoodState(isLoading = true)
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

    /*fun getFoodId(list: List<FoodCategoryDto>): String {
        val id = list.last().id
        val num = id.substring(2).toInt() + 1
        return when {
            num >= 10 -> "FC0${num}"
            num >= 100 -> "FC${num}"
            else -> "FC00${num}"
        }
    }*/

    fun getFoodId(): String {
        return "F002"
    }
}