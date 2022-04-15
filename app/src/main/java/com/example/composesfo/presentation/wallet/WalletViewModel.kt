package com.example.composesfo.presentation.wallet

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.common.UiEvent
import com.example.composesfo.data.remote.dto.WalletDto
import com.example.composesfo.domain.useCase.CreateWalletUseCase
import com.example.composesfo.domain.useCase.GetWalletUseCase
import com.example.composesfo.presentation.foodDetails.FoodDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val createWalletUseCase: CreateWalletUseCase
) : ViewModel() {
    private val _state = mutableStateOf(WalletState())
    val state: State<WalletState> = _state

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var name by mutableStateOf("")
        private set

    var cardNumber by mutableStateOf("")
        private set

    var expiryDate by mutableStateOf("")
        private set

    var cvcNumber by mutableStateOf("")
        private set

    init {
        getWallet(CurrentUserState.userId)
    }

    private fun getWallet(userId: String) {
        getWalletUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = WalletState(wallet = result.data)
                }
                is Resource.Error -> {
                    _state.value = WalletState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = WalletState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun createWallet(userId: String, walletDto: WalletDto) {
        createWalletUseCase(userId, walletDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = WalletState(wallet = result.data)
                }
                is Resource.Error -> {
                    _state.value = WalletState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = WalletState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onNameChange(text: String) {
        name = text
    }

    fun onCardNumberChange(text: String) {
        cardNumber = text
    }

    fun onExpiryDateChange(text: String) {
        expiryDate = text
    }

    fun onCVCNumberChange(text: String) {
        cvcNumber = text
    }

    fun getReloadedAmount(currentAmount: Int, reloadAmount: Int) : Int {
        return currentAmount + reloadAmount
    }

    fun checkNullField(userId: String, walletDto: WalletDto): Boolean {
        if (cardNumber.isBlank() || cvcNumber.isBlank() || name.isBlank() || expiryDate.isBlank()) {
            sendUiEvent(
                UiEvent.ShowSnackbar(
                message = "Please enter all fields"
            ))
            return false
        }
        createWallet(userId, walletDto)
        return true
    }

    fun isReloadSuccessful(error: String): Boolean {
        if (error.isNotBlank()) {
            return false
        }
        sendUiEvent(
            UiEvent.ShowSnackbar(
            message = "Reload Successful"
        ))
        return true
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}