package com.example.composesfo.presentation.foodDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Constants
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.common.UiEvent
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.domain.useCase.CreateCartUseCase
import com.example.composesfo.domain.useCase.GetCartFoodUseCase
import com.example.composesfo.domain.useCase.GetFoodUseCase
import com.example.composesfo.presentation.cart.CartState
import com.example.composesfo.presentation.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val getFoodUseCase: GetFoodUseCase,
    private val createCartUseCase: CreateCartUseCase,
    private val getCartFoodUseCase: GetCartFoodUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(FoodDetailsState())
    val state: State<FoodDetailsState> = _state

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    var stateQuantity by mutableStateOf(0)
        private set

    init {
        stateQuantity = 1
        savedStateHandle.get<String>(Constants.PARAM_FOOD_ID)?.let { foodId ->
            getFood(foodId)
        }
        if (CurrentUserState.cartFoodId.isNotBlank()) {
            getFood(CurrentUserState.cartFoodId)
            getCartFood(CurrentUserState.userId, CurrentUserState.cartFoodId)
            _cartState.value.cart?.let {
                stateQuantity = it.quantity.toInt()
            }

        }
    }

    private fun getFood(foodId: String) {
        getFoodUseCase(foodId).onEach { result ->
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

    fun createCart(userId: String, foodId: String, cartDto: CartDto) {
        createCartUseCase(userId, foodId, cartDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _cartState.value = CartState(cart = result.data)
                }
                is Resource.Error -> {
                    _cartState.value = CartState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCartFood(userId: String, foodId: String) {
        getCartFoodUseCase(userId, foodId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _cartState.value = CartState(cart = result.data)
                }
                is Resource.Error -> {
                    _cartState.value = CartState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun increaseQuantity() {
        stateQuantity += 1
    }

    fun decreaseQuantity() {
        if (stateQuantity > 1)
            stateQuantity -= 1
    }

    fun isCartAdded(error: String): Boolean {
        if (error.isNotBlank()) {
            return false
        }
        return true
    }
}