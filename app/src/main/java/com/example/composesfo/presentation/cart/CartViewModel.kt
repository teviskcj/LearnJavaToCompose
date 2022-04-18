package com.example.composesfo.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.useCase.DeleteCartItemUseCase
import com.example.composesfo.domain.useCase.DeleteCartListUseCase
import com.example.composesfo.domain.useCase.GetCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val deleteCartListUseCase: DeleteCartListUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CartListState())
    val state: State<CartListState> = _state

    private val _stateDeleteCart = mutableStateOf(DeleteCartState())
    val stateDeleteCart: State<DeleteCartState> = _stateDeleteCart

    var expanded by  mutableStateOf(false)
        private set

    var openDialog by mutableStateOf(false)
        private set

    init {
        getCartList(CurrentUserState.userId)
    }

     private fun getCartList(userId: String) {
        getCartUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CartListState(cartList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = CartListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = CartListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCartList(userId: String) {
        deleteCartListUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateDeleteCart.value = DeleteCartState(userId = result.data)
                }
                is Resource.Error -> {
                    _stateDeleteCart.value = DeleteCartState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateDeleteCart.value = DeleteCartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCartItem(userId: String, foodId: String) {
        deleteCartItemUseCase(userId, foodId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateDeleteCart.value = DeleteCartState(userId = result.data)
                    getCartList(userId)
                }
                is Resource.Error -> {
                    _stateDeleteCart.value = DeleteCartState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateDeleteCart.value = DeleteCartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addItem(quantity: Int): Int {
        return quantity
    }

    fun addTotalAmount(quantity: Int, price: Int): Int {
        return quantity * price
    }

    fun onExpandedChange(boolean: Boolean) {
        expanded = boolean
    }

    fun onOpenDialogChange(boolean: Boolean) {
        openDialog = boolean
    }
}