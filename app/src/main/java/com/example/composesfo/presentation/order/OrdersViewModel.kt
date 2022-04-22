package com.example.composesfo.presentation.order

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.domain.model.OrderView
import com.example.composesfo.domain.useCase.GetOrdersUseCase
import com.example.composesfo.presentation.ui.theme.green
import com.example.composesfo.presentation.ui.theme.yellowColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel()  {
    private val _state = mutableStateOf(OrderDetailState())
    val state: State<OrderDetailState> = _state

    var currentOrderList = mutableListOf<OrderView>()
    var passOrderList = mutableListOf<OrderView>()

    var tabIndex by mutableStateOf(0)
        private set

    var showCurrentOrder by mutableStateOf(true)
        private set

    init {
        getOrderList(CurrentUserState.userId)
    }

    private fun getOrderList(userId: String) {
        getOrdersUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = OrderDetailState(orderView = result.data ?: emptyList())
                    if (_state.value.orderView.isNotEmpty()) {
                        _state.value.orderView.forEach {
                            if (it.state != "D" && it.state != "R") {
                                currentOrderList.add(it)
                            }
                            if (it.state == "D") {
                                passOrderList.add(it)
                            }
                        }
                        //orderList = _state.value.orderView as MutableList<OrderView>
                    }
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

    fun getStateColor(text: String): androidx.compose.ui.graphics.Color {
        return if (text == "P") {
            yellowColor
        } else {
            green
        }
    }

    fun getStateName(text: String): String{
        return if (text == "P") {
            "Preparing"
        } else if (text == "S") {
            "Sending"
        } else {
            ""
        }
    }

    fun onTabIndexChange(index: Int) {
        tabIndex = index
    }

    fun onShowCurrentOrderChange(boolean: Boolean) {
        showCurrentOrder = boolean
    }
}