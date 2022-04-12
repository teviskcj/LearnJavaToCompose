package com.example.composesfo.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.Resource
import com.example.composesfo.common.UiEvent
import com.example.composesfo.domain.model.UserLogin
import com.example.composesfo.domain.useCase.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userLoginUseCase: UserLoginUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var phone by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showPassword by  mutableStateOf(false)
        private set


    init {
        getUsers()
    }

    private fun getUsers() {
        userLoginUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = LoginState(users = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = LoginState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onPhoneChange(text: String) {
        phone = text
    }

    fun onPasswordChange(text: String) {
        password = text
    }

    fun showPasswordChange(boolean: Boolean) {
        showPassword = boolean
    }

    fun checkNullField(): Boolean {
        if (phone.isBlank() || password.isBlank()) {
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Please enter phone and password"
            ))
            return false
        }
        return true
    }
    fun matchUserCredentials(): Boolean {

        val list = _state.value.users
        val user = findUser(list)

        if (user?.password != password) {
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Invalid phone and password"
            ))
            return false
        }
        return true
    }

    private fun findUser(list: List<UserLogin>): UserLogin? {
        for (user in list) {
            if (phone == user.phone) {
                return user
            }
        }
        return null
    }



    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}