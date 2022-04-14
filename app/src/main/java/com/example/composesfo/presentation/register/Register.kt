package com.example.composesfo.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.StoreUserPhone
import com.example.composesfo.common.UiEvent
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.presentation.component.HeaderImage
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.ComposeSFOTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserPhone(context)
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier
            .fillMaxSize()) {
            Column {

                HeaderImage()
                RegisterForm(
                    navController = navController,
                    name = viewModel.name,
                    phone = viewModel.phone,
                    password = viewModel.password,
                    onNameChange = viewModel::onNameChange,
                    onPhoneChange = viewModel::onPhoneChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    checkNullField = viewModel::checkNullField,
                    createUser = viewModel::createUser,
                    isRegisterSuccessful = viewModel::isRegisterSuccessful,
                    error = state.error,
                    scope = scope,
                    dataStore = dataStore
                )
            }
        }
    }
}

@Composable
fun RegisterForm(
    navController: NavController,
    name: String,
    phone: String,
    password: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    checkNullField: () -> Boolean,
    createUser: (String, UserDto) -> Unit,
    isRegisterSuccessful: (String) -> Boolean,
    error: String,
    scope: CoroutineScope,
    dataStore: StoreUserPhone
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // navigator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.navigate(route = Screen.LoginScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 22.sp
                )

            }

            Button(
                onClick = {  },
                enabled = false,
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "SIGN UP",
                    fontSize = 22.sp
                )

            }
        }

        Spacer(modifier = Modifier
            .padding(20.dp)
            .weight(1f))

        // Text Field
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = { stringResource(R.string.name) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Person, null) },
            trailingIcon = {
                IconButton(
                    onClick = { onNameChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { onPhoneChange(it) },
            label = { stringResource(R.string.phone_number) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Phone, null) },
            trailingIcon = {
                IconButton(
                    onClick = { onPhoneChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = { stringResource(R.string.password) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
            trailingIcon = {
                IconButton(
                    onClick = { onPasswordChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier
            .padding(20.dp)
            .weight(1f))

        Button(
            onClick = {
                if (checkNullField()) {
                    val userDto = UserDto(name,password,phone)
                    createUser(phone, userDto)

                    /*scope.launch {
                        dataStore.savePhone(phone)
                    }*/

                    CurrentUserState.userId = phone

                    if (isRegisterSuccessful(error)) {
                        navController.navigate(route = Screen.HomeScreen.route) {
                            navController.backQueue.clear()
                        }
                    }
                }

            },
            modifier = Modifier
                .height(60.dp)
        ) {
            Text(
                text = "REGISTER NOW",
                fontSize = 22.sp
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ComposeSFOTheme {
        RegisterScreen(navController = rememberNavController())
    }
}