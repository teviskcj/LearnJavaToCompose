package com.example.composesfo.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.example.composesfo.presentation.component.TextFieldWithIcon
import com.example.composesfo.presentation.component.TextFieldWithShowPasswordIcon
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton
import kotlinx.coroutines.CoroutineScope

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
            .padding(30.dp, 20.dp)
    ) {

        // navigator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                enabled = false,
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.log_in),
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier
                .weight(0.2f)
            )

            Button(
                onClick = {
                    navController.navigate(route = Screen.RegisterScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    fontSize = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier
            .padding(20.dp)
            .weight(1f))

        // Text Field
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

        TextFieldWithShowPasswordIcon(
            text = password,
            onTextChange = onPasswordChange,
            label = stringResource(R.string.password),
            painter = painterResource(id = R.drawable.ic_password),
            iconColor = AllButton,
            showPassword = showPassword,
            showPasswordChange = showPasswordChange,
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
                navController.navigate(route = Screen.AdminHomeScreen.route)
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.log_in),
                fontSize = 22.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = stringResource(R.string.login_as_user),
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
                    text = stringResource(R.string.login_as_admin),
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