package com.example.composesfo.presentation.payment

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.common.UiEvent
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.data.remote.dto.OrderDto
import com.example.composesfo.domain.useCase.CreateCartOrderUseCase
import com.example.composesfo.domain.useCase.CreateOrderUseCase
import com.example.composesfo.domain.useCase.DeleteCartListUseCase
import com.example.composesfo.domain.useCase.GetCartUseCase
import com.example.composesfo.presentation.cart.CartListState
import com.example.composesfo.presentation.cart.CartState
import com.example.composesfo.presentation.cart.DeleteCartState
import com.example.composesfo.presentation.component.getDate
import com.example.composesfo.presentation.component.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val createCartOrderUseCase: CreateCartOrderUseCase,
    private val deleteCartListUseCase: DeleteCartListUseCase
) : ViewModel() {
    private val _stateOrder = mutableStateOf(OrderState())
    val stateOrder: State<OrderState> = _stateOrder

    private val _stateCart = mutableStateOf(CartListState())
    val stateCart: State<CartListState> = _stateCart

    private val _stateCartOrder = mutableStateOf(CartState())
    val stateCartOrder: State<CartState> = _stateCartOrder

    private val _stateDeleteCartList = mutableStateOf(DeleteCartState())
    val stateDeleteCartList: State<DeleteCartState> = _stateDeleteCartList

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var name by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var address by mutableStateOf("")
        private set

    var city by mutableStateOf("")
        private set

    init {
        getCartList(CurrentUserState.userId)
    }

    fun createOrder(userId: String, orderId: String, orderDto: OrderDto) {
        createOrderUseCase(userId, orderId, orderDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateOrder.value = OrderState(order = result.data)
                }
                is Resource.Error -> {
                    _stateOrder.value = OrderState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateOrder.value = OrderState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCartList(userId: String) {
        getCartUseCase(userId).onEach { result ->
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

    fun createCartOrder(userId: String, orderId: String, foodId: String, cartDto: CartDto) {
        createCartOrderUseCase(userId,orderId, foodId, cartDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateCartOrder.value = CartState(cart = result.data)
                }
                is Resource.Error -> {
                    _stateCartOrder.value = CartState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateCartOrder.value = CartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCartList(userId: String) {
        deleteCartListUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateDeleteCartList.value = DeleteCartState(userId = result.data)
                }
                is Resource.Error -> {
                    _stateDeleteCartList.value = DeleteCartState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateDeleteCartList.value = DeleteCartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onNameChange(text: String) {
        name = text
    }

    fun onPhoneChange(text: String) {
        phone = text
    }

    fun onAddressChange(text: String) {
        address = text
    }

    fun onCityChange(text: String) {
        city = text
    }

    fun createOrderId(): String {
        val randomNumber = (Math.random() * 9).toInt()
        return getDate() + getTime() + randomNumber.toString()
    }

    fun checkNullField(): Boolean {
        if (phone.isBlank() || address.isBlank() || name.isBlank() || city.isBlank()) {
            sendUiEvent(
                UiEvent.ShowSnackbar(
                message = "Please enter all fields"
            ))
            return false
        }
        return true
    }

    fun isOrderSuccessful(error: String): Boolean {
        if (error.isNotBlank()) {
            return false
        }
        sendUiEvent(
            UiEvent.ShowSnackbar(
            message = "Order Successful"
        ))
        return true
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}