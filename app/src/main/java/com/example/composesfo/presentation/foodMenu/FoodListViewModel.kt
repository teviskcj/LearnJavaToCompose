package com.example.composesfo.presentation.foodMenu

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.useCase.foodCategoryUseCase.FoodCategoryUseCase
import com.example.composesfo.domain.useCase.foodUseCase.FoodUseCase
import com.example.composesfo.domain.useCase.foodUseCase.GetFoodsUseCase
import com.example.composesfo.presentation.admin.adminAddCategory.GetCategoriesState
import com.example.composesfo.presentation.ui.theme.AllButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase,
    private val foodCategoryUseCase: FoodCategoryUseCase,
    private val foodUseCase: FoodUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FoodListState())
    val state: State<FoodListState> = _state

    private val _stateCategory = mutableStateOf(GetCategoriesState())
    val stateCategory: State<GetCategoriesState> = _stateCategory

    var categoryList = mutableListOf<FoodCategoryDto>()

    var stateList = mutableListOf("")

    var stateType by mutableStateOf("")
        private set

    var typePositionList = mutableListOf(0)
        private set

    init {
        getCategoryList()
        getFoods()
    }

    private fun getCategoryList() {
        foodCategoryUseCase.getFoodCategoriesUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateCategory.value = GetCategoriesState(categoryList = result.data ?: emptyList())
                    if (_stateCategory.value.categoryList.isNotEmpty()) {
                        categoryList = _stateCategory.value.categoryList as MutableList<FoodCategoryDto>
                    }
                }
                is Resource.Error -> {
                    _stateCategory.value = GetCategoriesState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateCategory.value = GetCategoriesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getFoods() {
        getFoodsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = FoodListState(foods = result.data ?: emptyList())
                    //addStateList(_state.value.foods)
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

    fun getCategoryList(list: List<FoodCategoryDto>): List<String> {
        val tempList = mutableListOf<String>()
        if (list.isEmpty()) {
            return emptyList()
        }
        list.forEach {
            if (stateList.first().isEmpty()) stateList[0] = it.category_name
            if (stateList.first().isNotEmpty()) stateList.add(it.category_name)
            tempList.add(it.category_name)
            //list1.add(it.category_name)
            //list1.distinct()
        }
        stateList = stateList.distinct() as MutableList<String>

        if (stateType.isEmpty()) {
            stateType = tempList[0]
        }

        return tempList
    }

    fun getCategoryFoodList(text: String, list: List<FoodCategoryDto>): List<String> {
        list.forEach {
            if (it.category_name == text) {
                return it.foods
            }
        }
        return listOf("")
    }

    fun getFoodListByCategory(categoryFoodList: List<String>, list: List<Food>): List<Food> {
        val tempList = mutableListOf<Food>()

        /*if (type == "Popular") {
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
        }*/
        list.forEach {
            if (categoryFoodList.contains(it.food_name)) {
                tempList.add(it)
            }
        }
        return tempList
    }



    private fun addStateList(list: List<Food>) {
        var tempList = arrayListOf<String>()
        /*if (list.isNotEmpty()) {
            list.forEach {
                if (it.foodPopular == "Y") {
                    //tempList.add("Popular")
                    tempList = (listOf("Popular") + tempList) as ArrayList<String>
                }
                tempList.add(it.foodCategory)
            }
        }*/
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

        /*if (type == "Popular") {
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
        }*/
        return tempList
    }

    fun scrollToByType(coroutineScope: CoroutineScope, scrollState: ScrollState) {
        coroutineScope.launch {
            val tempList = stateList.toList()
            val positionList = typePositionList.toIntArray()
            val iterator = stateList.iterator()

            tempList.forEachIndexed { index, s ->
                if (stateType == s) {
                    scrollState.animateScrollTo(typePositionList[index])
                }
            }

            /*while (iterator.hasNext()) {
                val item = iterator.next()
                Log.d("testing123","item is $item")
                if (item == stateType) {
                    scrollState.animateScrollTo(typePositionList[0])
                }
            }*/
            /*with(stateList.iterator()) {
                forEach {
                    if (stateType == it) {
                        scrollState.animateScrollTo(typePositionList[0])
                    }
                }
            }*/
            /*stateList.forEachIndexed { index, s ->
                if (stateType == s) {
                    scrollState.animateScrollTo(typePositionList[0])
                    return@forEachIndexed
                }
            }*/
        }
    }

    fun scrollToByCategory(coroutineScope: CoroutineScope, lazyListState: LazyListState) {
        coroutineScope.launch {
            val tempList = stateList.toList()
            lazyListState.firstVisibleItemIndex

            tempList.forEachIndexed { index, s ->
                if (stateType == s) {
                    lazyListState.animateScrollToItem(index)
                }
            }
        }
    }

    fun addTypePositionList(type: String, position: Int) {
        val size = typePositionList.size
        val tempList = stateList.distinct()

        if (tempList.size == typePositionList.size) return
        if (typePositionList.size > tempList.size) return

        /*if (type == stateList[1] && typePositionList.size == 1) {
            typePositionList.add(position - 650)
            Log.d("testing123","position is ${typePositionList.toString()}")
        }*/
        typePositionList.add(position + 2200)
        //Log.d("testing123","stateList is $tempList")
        //Log.d("testing123","position is ${typePositionList.size}")
        /*stateList.forEachIndexed { index, category ->
            if (type == category && size == index && typePositionList.size != 0 && size <= index) {
                typePositionList.add(position - 650)
                Log.d("testing123","position is $position")
            }
            typePositionList.add(position - 650)
        }*/
        /*if (type == stateList[1] && typePositionList.size == 1) {
            typePositionList.add(position - 650)
        }
        if (type == stateList[2] && typePositionList.size == 2) {
            typePositionList.add(position - 650)
        }*/
    }

    fun changeTypeByScrollPosition(currentValue: Int, maxValue: Int) {
        val tempList = typePositionList.toIntArray()
        Log.d("testing123","current value is $currentValue")
        Log.d("testing123","positionList is ${typePositionList}")

        tempList.forEachIndexed { index, position ->
            if (index != tempList.lastIndex) {
                if (currentValue in position..tempList[index+1]) {
                    stateType = stateList[index]
                }
            }

            if (index == tempList.lastIndex) {
                if (currentValue in position..maxValue) {
                    stateType = stateList[index]
                }
            }
        }

        /*typePositionList.forEachIndexed { index, position ->
            if (index != typePositionList.lastIndex) {
                if (currentValue in position..typePositionList[index+1]) {
                    stateType = stateList[index]
                    Log.d("testing123","state is $stateType")
                }
            }

            if (index == typePositionList.lastIndex) {
                if (currentValue in position..maxValue) {
                    stateType = stateList[index]
                    Log.d("testing123","stateLast is $stateType")
                }
            }
        }*/
    }
}