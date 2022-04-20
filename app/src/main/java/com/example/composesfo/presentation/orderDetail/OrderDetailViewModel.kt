package com.example.composesfo.presentation.orderDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Constants
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.useCase.GetCartOrderUseCase
import com.example.composesfo.domain.useCase.GetOrderUseCase
import com.example.composesfo.presentation.cart.CartListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase,
    private val getCartOrderUseCase: GetCartOrderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(OrderDetailState())
    val state: State<OrderDetailState> = _state

    private val _stateCart = mutableStateOf(CartListState())
    val stateCart: State<CartListState> = _stateCart

    init {
        savedStateHandle.get<String>(Constants.PARAM_ORDER_ID)?.let { orderId ->
            getOrderList(CurrentUserState.userId, orderId)
            getCartList(CurrentUserState.userId, orderId)
        }
    }

    private fun getOrderList(userId: String, orderId: String) {
        getOrderUseCase(userId, orderId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = OrderDetailState(orderDetail = result.data)
                }
                is Resource.Error -> {
                    _state.value = OrderDetailState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = OrderDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCartList(userId: String, orderId: String) {
        getCartOrderUseCase(userId, orderId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateCart.value = CartListState(cartList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _stateCart.value = CartListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateCart.value = CartListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}