package com.example.composesfo.presentation.admin.adminAddCategory

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Constants
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.model.Food
import com.example.composesfo.domain.useCase.foodCategoryUseCase.FoodCategoryUseCase
import com.example.composesfo.domain.useCase.foodUseCase.FoodUseCase
import com.example.composesfo.presentation.foodMenu.FoodListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class AdminAddCategoryViewModel @Inject constructor(
    private val foodCategoryUseCase: FoodCategoryUseCase,
    private val foodUseCase: FoodUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(AddCategoryState())
    val state: State<AddCategoryState> = _state

    private val _stateGetCategories = mutableStateOf(GetCategoriesState())
    val stateGetCategories: State<GetCategoriesState> = _stateGetCategories

    private val _stateGetCategory = mutableStateOf(GetCategoryState())
    val stateGetCategory: State<GetCategoryState> = _stateGetCategory

    private val _stateFoods = mutableStateOf(FoodListState())
    val stateFoods: State<FoodListState> = _stateFoods

    var foodList = mutableStateListOf("")

    var selectedFoodList = mutableStateListOf<String>()

    var category by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var isFoodsAdded by mutableStateOf(false)
        private set

    init {
        savedStateHandle.get<String>(Constants.PARAM_CATEGORY_ID)?.let { categoryId ->
            getCategoryById(categoryId)
        }
        getCategoryList()
        getFoods()
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
                    _stateGetCategories.value = GetCategoriesState(categoryList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _stateGetCategories.value = GetCategoriesState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateGetCategories.value = GetCategoriesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCategoryById(categoryId: String) {
        foodCategoryUseCase.getFoodCategoryUseCase(categoryId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateGetCategory.value = GetCategoryState(category = result.data)
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

    private fun getFoods() {
        foodUseCase.getFoodsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateFoods.value = FoodListState(foods = result.data ?: emptyList())
                    addFoodList(_stateFoods.value.foods)
                }
                is Resource.Error -> {
                    _stateFoods.value = FoodListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateFoods.value = FoodListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addFoodList(list: List<Food>) {
        val tempList = mutableStateListOf<String>()
        if (list.isNotEmpty()) {
            list.forEach {
                tempList.add(it.food_name)
            }
        }
        foodList = tempList
    }

    fun addFoodItem(foodName: String) {
        foodList.add(foodName)
        foodList.sort()
    }

    fun removeFoodList(foodName: String) {
        /*for (index in 0 until foodList.size-1) {
            if (foodList[index] == food) {
                foodList.removeAt(index)
            }
        }*/
        foodList.remove(foodName)
    }

     fun getFoodList(list: List<Food>): ArrayList<String> {
        val tempList = arrayListOf<String>()
        if (list.isNotEmpty()) {
            list.forEach {
                tempList.add(it.food_name)
            }
        }
        return tempList
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

    fun getCategoryId(list: List<FoodCategoryDto>): String {
        val id = list.last().id
        val num = id.substring(2).toInt() + 1
        return when {
            num >= 10 -> "FC0${num}"
            num >= 100 -> "FC${num}"
            else -> "FC00${num}"
        }
    }

    fun addSelectedFoodList(foodName: String) {
        selectedFoodList.add(foodName)
        selectedFoodList.sort()
    }

    fun removeSelectedFoodItem(foodName: String) {
        selectedFoodList.remove(foodName)
    }

    fun addExistingFoodList(categoryList: List<String>, foods: List<String>) {
        if (isFoodsAdded) {
            return
        }

        for (index in 0..foods.lastIndex) {
            for (i in 0 until categoryList.lastIndex) {
                if (foods[index] == categoryList[i]) {
                    addSelectedFoodList(foods[index])
                    removeFoodList(foods[index])
                    isFoodsAdded = true
                    //break
                }
            }
        }
    }

    fun getSelectedFoodList(list: List<String>): List<String> {
        if (list.isNullOrEmpty()) {
            return listOf("")
        }
        return list
    }
}