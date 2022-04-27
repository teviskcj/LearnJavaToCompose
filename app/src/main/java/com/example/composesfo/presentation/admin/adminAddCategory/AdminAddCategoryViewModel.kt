package com.example.composesfo.presentation.admin.adminAddCategory

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.useCase.foodCategoryUseCase.FoodCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminAddCategoryViewModel @Inject constructor(
    private val foodCategoryUseCase: FoodCategoryUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AddCategoryState())
    val state: State<AddCategoryState> = _state

    private val _stateGetCategory = mutableStateOf(GetCategoryState())
    val stateGetCategory: State<GetCategoryState> = _stateGetCategory

    var category by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    init {
        getCategoryList()
    }

    fun createCategory(categoryId: String, foodCategoryDto: FoodCategoryDto) {
        foodCategoryUseCase.createFoodCategoryUseCase(categoryId, foodCategoryDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = AddCategoryState(foodCategoryDto = result.data)
                }
                is Resource.Error -> {
                    _state.value = AddCategoryState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = AddCategoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCategoryList() {
        foodCategoryUseCase.getFoodCategoriesUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateGetCategory.value = GetCategoryState(categoryList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _stateGetCategory.value = GetCategoryState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateGetCategory.value = GetCategoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onCategoryChange(newString: String) {
        category = newString
    }

    fun onDescriptionChange(newString: String) {
        description = newString
    }

    fun createCategoryId(count: Int): String {
        return when {
            count + 1 >= 10 -> "FC0${count+1}"
            count + 1 >= 100 -> "FC${count+1}"
            else -> "FC00${count+1}"
        }
    }
}