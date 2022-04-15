package com.example.composesfo.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.common.UiEvent
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.data.remote.dto.WalletDto
import com.example.composesfo.domain.useCase.CreateWalletUseCase
import com.example.composesfo.domain.useCase.UserRegisterUseCase
import com.example.composesfo.presentation.wallet.WalletState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val createWalletUseCase: CreateWalletUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _stateWallet = mutableStateOf(WalletState())
    val stateWallet: State<WalletState> = _stateWallet

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var name by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun createUser(userId: String, userDto: UserDto) {
        userRegisterUseCase(userId, userDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = RegisterState(userRegister = result.data)
                }
                is Resource.Error -> {
                    _state.value = RegisterState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createWallet(userId: String, walletDto: WalletDto) {
        createWalletUseCase(userId, walletDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateWallet.value = WalletState(wallet = result.data)
                }
                is Resource.Error -> {
                    _stateWallet.value = WalletState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateWallet.value = WalletState(isLoading = true)
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

    fun onPasswordChange(text: String) {
        password = text
    }

    fun checkNullField(): Boolean {
        if (phone.isBlank() || password.isBlank() || name.isBlank()) {
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Please enter name, phone and password"
            ))
            return false
        }
        return true
    }

    fun isRegisterSuccessful(error: String): Boolean {
        if (error.isNotBlank()) {
            return false
        }
        sendUiEvent(UiEvent.ShowSnackbar(
            message = "Register Successful"
        ))
        return true
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}