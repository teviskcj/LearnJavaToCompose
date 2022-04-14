package com.example.composesfo.presentation.cart

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Constants
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.common.StoreUserPhone
import com.example.composesfo.domain.useCase.GetCartUseCase
import com.example.composesfo.presentation.foodMenu.FoodListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    savedStateHandle: SavedStateHandle,
    application: Application
) : ViewModel() {

    private val _state = mutableStateOf(CartListState())
    val state: State<CartListState> = _state

    val dataStore = StoreUserPhone(application)
    val userPhone = dataStore.getPhone

    init {
        /*savedStateHandle.get<String>(Constants.PARAM_USER_ID)?.let { userId ->
            getCartList(userId)
        }*/
        /*viewModelScope.launch {
            userPhone.first()?.let { getCartList(it) }
        }*/
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
}