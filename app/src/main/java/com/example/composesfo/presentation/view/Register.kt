package com.example.composesfo.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.example.composesfo.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.ComposeSFOTheme

@Composable
fun RegisterScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (
                backgroundCard, loginNavigator, signupNavigator,
                nameInput, phoneNumberInput, passwordInput, registerButton,
            ) = createRefs()

            // header image
            Box(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(backgroundCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .aspectRatio(1.25f),
            ) {
                Image(painter = painterResource(id = R.drawable.login_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                )

                Image(painter = painterResource(id = R.drawable.wujiak),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            // navigator
            Button(
                onClick = { navController.navigate(route = Screen.LoginScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
                    .constrainAs(loginNavigator) {
                        start.linkTo(parent.start)
                        top.linkTo(backgroundCard.bottom, 20.dp)
                    }
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 22.sp
                )

            }

            Button(
                onClick = { /*TODO*/ },
                enabled = false,
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
                    .constrainAs(signupNavigator) {
                        start.linkTo(loginNavigator.end)
                        top.linkTo(loginNavigator.top)
                        bottom.linkTo(loginNavigator.bottom)
                    }
            ) {
                Text(
                    text = "SIGN UP",
                    fontSize = 22.sp
                )

            }

            createHorizontalChain(
                loginNavigator, signupNavigator,
                chainStyle = ChainStyle.Spread
            )

            // Text Field
            val name = remember { mutableStateOf("") }
            val phoneNumber = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { stringResource(R.string.name) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Person, null) },
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.constrainAs(nameInput) {
                    top.linkTo(loginNavigator.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            OutlinedTextField(
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                label = { stringResource(R.string.phone_number) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Phone, null) },
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.constrainAs(phoneNumberInput) {
                    top.linkTo(nameInput.bottom, 20.dp)
                    start.linkTo(nameInput.start)
                    end.linkTo(nameInput.end)
                }
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { stringResource(R.string.password) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Lock, null) },
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.constrainAs(passwordInput) {
                    top.linkTo(phoneNumberInput.bottom, 20.dp)
                    start.linkTo(nameInput.start)
                    end.linkTo(nameInput.end)
                }
            )

            Button(
                onClick = {
                    navController.navigate(route = Screen.HomeScreen.route) {
                        navController.backQueue.clear()
                    }
                          },
                modifier = Modifier
                    .height(60.dp)
                    .constrainAs(registerButton) {
                        top.linkTo(passwordInput.bottom, 30.dp)
                        start.linkTo(nameInput.start)
                        end.linkTo(nameInput.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = "REGISTER NOW",
                    fontSize = 22.sp
                )

            }
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