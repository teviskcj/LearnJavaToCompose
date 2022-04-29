package com.example.composesfo.presentation.admin.foodCategoryMenu

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.useCase.foodCategoryUseCase.FoodCategoryUseCase
import com.example.composesfo.presentation.admin.adminAddCategory.GetCategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FoodCategoryViewModel @Inject constructor(
    private val foodCategoryUseCase: FoodCategoryUseCase
) : ViewModel() {
    private val _stateGetCategory = mutableStateOf(GetCategoryState())
    val stateGetCategory: State<GetCategoryState> = _stateGetCategory

    var isSelected by mutableStateOf(false)
        private set

    init {
        getCategoryList()
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

    fun isSelectedChange(boolean: Boolean) {
        isSelected = boolean
    }
}