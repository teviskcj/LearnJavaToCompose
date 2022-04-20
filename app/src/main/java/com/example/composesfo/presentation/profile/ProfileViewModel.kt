package com.example.composesfo.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.QuestionDto
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.domain.useCase.CreateQuestionUseCase
import com.example.composesfo.domain.useCase.GetQuestionUseCase
import com.example.composesfo.domain.useCase.GetUserUseCase
import com.example.composesfo.domain.useCase.UserRegisterUseCase
import com.example.composesfo.presentation.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userRegisterUseCase: UserRegisterUseCase,
    private val createQuestionUseCase: CreateQuestionUseCase,
    private val getQuestionUseCase: GetQuestionUseCase
) : ViewModel() {
    private val _state = mutableStateOf(UserProfileState())
    val state: State<UserProfileState> = _state

    private val _stateRegister = mutableStateOf(RegisterState())
    val stateRegister: State<RegisterState> = _stateRegister

    private val _stateQuestion = mutableStateOf(QuestionState())
    val stateQuestion: State<QuestionState> = _stateQuestion

    var showNameField by  mutableStateOf(false)
        private set

    var showPasswordField by mutableStateOf(false)
        private set

    var showQuestionField by mutableStateOf(false)
        private set

    var name by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var answerOne by mutableStateOf("")
        private set

    var answerTwo by mutableStateOf("")
        private set

    init {
        getUserProfile(CurrentUserState.userId)
        getQuestion(CurrentUserState.userId)
    }

    private fun getUserProfile(userId: String) {
        getUserUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = UserProfileState(userProfile = result.data)
                }
                is Resource.Error -> {
                    _state.value = UserProfileState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = UserProfileState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createUser(userId: String, userDto: UserDto) {
        userRegisterUseCase(userId, userDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateRegister.value = RegisterState(userRegister = result.data)
                }
                is Resource.Error -> {
                    _stateRegister.value = RegisterState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateRegister.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createQuestion(userId: String, questionDto: QuestionDto) {
        createQuestionUseCase(userId, questionDto).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateQuestion.value = QuestionState(question = result.data)
                }
                is Resource.Error -> {
                    _stateQuestion.value = QuestionState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateQuestion.value = QuestionState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getQuestion(userId: String) {
        getQuestionUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _stateQuestion.value = QuestionState(question = result.data)
                }
                is Resource.Error -> {
                    _stateQuestion.value = QuestionState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _stateQuestion.value = QuestionState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onShowNameFieldChange(boolean: Boolean) {
        showNameField = boolean
    }

    fun onShowPasswordFieldChange(boolean: Boolean) {
        showPasswordField = boolean
    }

    fun onShowQuestionFieldChange(boolean: Boolean) {
        showQuestionField = boolean
    }

    fun onNameChange(text: String) {
        name = text
    }

    fun onPasswordChange(text: String) {
        password = text
    }

    fun onConfirmPasswordChange(text: String) {
        confirmPassword = text
    }

    fun onAnswerOneChange(text: String) {
        answerOne = text
    }

    fun onAnswerTwoChange(text: String) {
        answerTwo = text
    }
}