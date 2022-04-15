package com.example.composesfo.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.example.composesfo.data.remote.dto.WalletDto
import com.example.composesfo.presentation.component.HeaderImage
import com.example.composesfo.presentation.component.TextFieldWithIcon
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.ComposeSFOTheme
import kotlinx.coroutines.CoroutineScope

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
                    createWallet = viewModel::createWallet,
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
    createWallet: (String, WalletDto) -> Unit,
    isRegisterSuccessful: (String) -> Boolean,
    error: String,
    scope: CoroutineScope,
    dataStore: StoreUserPhone
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 20.dp)
    ) {

        // navigator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(route = Screen.LoginScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 22.sp
                )

            }


            Spacer(modifier = Modifier
                .weight(0.2f)
            )

            Button(
                onClick = {  },
                enabled = false,
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
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
        TextFieldWithIcon(
            text = name,
            onTextChange = onNameChange,
            label = stringResource(R.string.name),
            painter = painterResource(id = R.drawable.ic_profile),
            iconColor = AllButton,
        )

        TextFieldWithIcon(
            text = phone,
            onTextChange = onPhoneChange,
            label = stringResource(R.string.phone_number),
            painter = painterResource(id = R.drawable.ic_phone),
            iconColor = AllButton,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        TextFieldWithIcon(
            text = password,
            onTextChange = onPasswordChange,
            label = stringResource(R.string.password),
            painter = painterResource(id = R.drawable.ic_password),
            iconColor = AllButton,
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
                    createWallet(phone, WalletDto(phone,0))

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
                .fillMaxWidth()
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