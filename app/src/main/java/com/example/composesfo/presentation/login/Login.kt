package com.example.composesfo.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
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
import com.example.composesfo.presentation.component.HeaderImage
import com.example.composesfo.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
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
                LoginForm(
                    navController = navController,
                    phone = viewModel.phone,
                    password = viewModel.password,
                    showPassword = viewModel.showPassword,
                    onPhoneChange = viewModel::onPhoneChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    showPasswordChange = viewModel::showPasswordChange,
                    checkNullField = viewModel::checkNullField,
                    matchUserCredentials = viewModel::matchUserCredentials,
                    scope = scope,
                    dataStore = dataStore
                )
            }
        }
    }
}

@Composable
fun LoginForm(
    navController: NavController,
    phone: String,
    password: String,
    showPassword: Boolean,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    showPasswordChange: (Boolean) -> Unit,
    checkNullField: () -> Boolean,
    matchUserCredentials: () -> Boolean,
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
                onClick = { /*TODO*/ },
                enabled = false,
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
                onClick = {
                          if (navController.backQueue.isEmpty()) {
                              navController.navigate(route = Screen.RegisterScreen.route)
                          } else {
                              navController.popBackStack()
                          } },
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "REGISTER",
                    fontSize = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier
            .padding(20.dp)
            .weight(1f))

        // Text Field
        OutlinedTextField(
            value = phone,
            onValueChange = { onPhoneChange(it) },
            label = { stringResource(id = R.string.phone_number) },
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
            label = { stringResource(id = R.string.password) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                if (showPassword) {
                    IconButton(
                        onClick = { showPasswordChange(false) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility),
                            null
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPasswordChange(true) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility_off),
                            null
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )



        Spacer(modifier = Modifier
            .padding(20.dp)
            .weight(1f))

        Button(
            onClick = {
                if (checkNullField() && matchUserCredentials()) {
                    /*scope.launch {
                        dataStore.savePhone(phone)
                    }*/
                    CurrentUserState.userId = phone

                    navController.navigate(route = Screen.HomeScreen.route) {
                        navController.backQueue.clear()
                    }
                }

            },
            modifier = Modifier
                .height(60.dp)
        ) {
            Text(
                text = "LOG IN",
                fontSize = 22.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Login As User",
                    fontSize = 18.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic
                )
            }

            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Login As Admin",
                    fontSize = 18.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}