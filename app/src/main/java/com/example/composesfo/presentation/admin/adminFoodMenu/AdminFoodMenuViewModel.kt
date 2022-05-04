package com.example.composesfo.presentation.admin.adminFoodMenu

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.useCase.foodCategoryUseCase.FoodCategoryUseCase
import com.example.composesfo.presentation.admin.adminAddCategory.GetCategoriesState
import com.example.composesfo.presentation.cart.DeleteCartState
import com.example.composesfo.presentation.ui.theme.AllButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminFoodMenuViewModel @Inject constructor(
    private val foodCategoryUseCase: FoodCategoryUseCase
) : ViewModel() {
    private val _stateGetCategory = mutableStateOf(GetCategoriesState())
    val stateGetCategories: State<GetCategoriesState> = _stateGetCategory

    private val _stateDeleteCategory = mutableStateOf(DeleteCategoryState())
    val stateDeleteCategory: State<DeleteCategoryState> = _stateDeleteCategory

    var categoryList = mutableListOf<FoodCategoryDto>()

    var list1 = mutableStateListOf<String>()

    var selectedCategory by mutableStateOf("")
        private set

    var toState by mutableStateOf(false)
        private set

    var tabIndex by mutableStateOf(0)
        private set

    var showDropDownMenu by mutableStateOf(false)
        private set

    init {
        getCategoryList()
    }

    fun reload() {
        getCategoryList()
    }

    private fun getCategoryList() {
        foodCategoryUseCase.getFoodCategoriesUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateGetCategory.value = GetCategoriesState(categoryList = result.data ?: emptyList())
                    if (_stateGetCategory.value.categoryList.isNotEmpty()) {
                        categoryList = _stateGetCategory.value.categoryList as MutableList<FoodCategoryDto>
                        Log.d("testing123", categoryList.toString())
                    }
                }
                is Resource.Error -> {
                    _stateGetCategory.value = GetCategoriesState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateGetCategory.value = GetCategoriesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCartList(categoryId: String) {
        foodCategoryUseCase.deleteFoodCategoryUseCase(categoryId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateDeleteCategory.value = DeleteCategoryState(categoryId = result.data)
                    list1.remove(result.data)
                    /*categoryList.forEach {
                        if (it.id == result.data) {
                            categoryList.remove(it)
                        }
                    }*/
                }
                is Resource.Error -> {
                    _stateDeleteCategory.value = DeleteCategoryState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateDeleteCategory.value = DeleteCategoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getColor(text: String): Color {
        return if (text == selectedCategory) {
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
            tempList.add(it.category_name)
            list1.add(it.category_name)
            //list1.distinct()
        }
        return tempList
    }

    fun getCategoryId(list: List<FoodCategoryDto>, categoryName: String): String {
        if (list.isEmpty()) {
            return ""
        }
        list.forEach {
            if (categoryName == it.category_name) {
                return it.id
            }
        }
        return ""
    }

    fun onTypeChange(text: String) {
        selectedCategory = text
    }

    fun onTabIndexChange(index: Int) {
        tabIndex = index
    }

    fun onShowDropDownMenuChange(boolean: Boolean) {
        showDropDownMenu = boolean
    }
}