package com.example.composesfo.presentation.admin.adminFoodMenu

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.useCase.foodCategoryUseCase.FoodCategoryUseCase
import com.example.composesfo.presentation.admin.adminAddCategory.GetCategoryState
import com.example.composesfo.presentation.ui.theme.AllButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminFoodMenuViewModel @Inject constructor(
    private val foodCategoryUseCase: FoodCategoryUseCase
) : ViewModel() {
    private val _stateGetCategory = mutableStateOf(GetCategoryState())
    val stateGetCategory: State<GetCategoryState> = _stateGetCategory

    var stateType by mutableStateOf("")
        private set

    var toState by mutableStateOf(false)
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

    fun getColor(text: String): Color {
        return if (text == stateType) {
            AllButton
        } else {
            Color.LightGray
        }
    }

    fun getCategoryList(list: List<FoodCategoryDto>): List<String> {
        val tempList = mutableListOf<String>()
        if (list.isEmpty()) {
            return emptyList()
        }
        list.forEach {
            tempList.add(it.category)
        }
        return tempList
    }

    fun onTypeChange(text: String) {
        stateType = text
    }
}