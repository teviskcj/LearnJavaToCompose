package com.example.composesfo.presentation.foodMenu

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.useCase.GetFoodsUseCase
import com.example.composesfo.presentation.ui.theme.AllButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FoodListState())
    val state: State<FoodListState> = _state

    var stateList = mutableListOf("")

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
                    addStateList(_state.value.foods)
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

    private fun addStateList(list: List<Food>) {
        var tempList = arrayListOf<String>()
        if (list.isNotEmpty()) {
            list.forEach {
                if (it.foodPopular == "Y") {
                    //tempList.add("Popular")
                    tempList = (listOf("Popular") + tempList) as ArrayList<String>
                }
                tempList.add(it.foodCategory)
            }
        }
        stateList = tempList.toSet().toList().toMutableList()
        stateType = stateList[0]
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

    fun getFoodListByType(type: String, list: List<Food>): List<Food> {
        val tempList = mutableListOf<Food>()

        if (type == "Popular") {
            list.forEach {
                if (it.foodPopular == "Y") {
                    tempList.add(it)
                }
            }
            return tempList
        }

        list.forEach {
            if (it.foodCategory == type) {
                tempList.add(it)
            }
        }
        return tempList
    }

}