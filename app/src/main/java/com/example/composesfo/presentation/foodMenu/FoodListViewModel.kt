package com.example.composesfo.presentation.foodMenu

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.useCase.GetFoodsUseCase
import com.example.composesfo.presentation.ui.theme.AllButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FoodListState())
    val state: State<FoodListState> = _state

    var stateType by mutableStateOf("")
        private set

    init {
        getFoods()
    }

    private fun getFoods() {
        getFoodsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = FoodListState(foods = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FoodListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = FoodListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onTypeChange(text: String) {
        stateType = text
    }

    fun getColor(text: String): androidx.compose.ui.graphics.Color {
        return if (text == stateType) {
            AllButton
        } else {
            LightGray
        }
    }
}