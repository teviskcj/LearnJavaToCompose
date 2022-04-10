package com.example.composesfo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composesfo.presentation.navigation.Navigation
import com.example.composesfo.presentation.ui.theme.ComposeSFOTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Navigation()
        }
    }
}

/*@Composable
fun RegisterScreen() {
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
                onClick = { *//*TODO*//* },
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
                onClick = { *//*TODO*//* },
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
                onClick = { *//*TODO*//* },
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
}*/


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSFOTheme {
        //RegisterScreen(navController = rememberNavController())
        Navigation()
    }
}