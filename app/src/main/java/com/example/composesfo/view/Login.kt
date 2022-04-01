package com.example.composesfo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
                .fillMaxSize(),
            constraintSet = loginScreenConstraintSet()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .layoutId("backgroundCard")
                .aspectRatio(1.25f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                
                Image(
                    painter = painterResource(id = R.drawable.wujiak), 
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // navigator
            Button(
                onClick = { /*TODO*/ },
                enabled = false,
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
                    .layoutId("loginNavigator")
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
                    .layoutId("signupNavigator")
            ) {
                Text(
                    text = "REGISTER",
                    fontSize = 22.sp
                )
            }

            // Text Field
            val phoneNumber = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            OutlinedTextField(
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                label = { stringResource(id = R.string.phone_number) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Phone, null) },
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("phoneNumberInput")
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { stringResource(id = R.string.password) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Lock, null) },
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("passwordInput")
            )

            Button(
                onClick = { navController.navigate(route = Screen.HomeScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("loginButton")
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 22.sp
                )
            }

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("loginAsUserButton")
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
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("loginAsAdminButton")
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

private fun loginScreenConstraintSet() : ConstraintSet {
    return ConstraintSet {
        val backgroundCard = createRefFor("backgroundCard")
        val loginNavigator = createRefFor("loginNavigator")
        val signupNavigator = createRefFor("signupNavigator")
        val phoneNumberInput = createRefFor("phoneNumberInput")
        val passwordInput = createRefFor("passwordInput")
        val loginButton = createRefFor("loginButton")
        val loginAsUserButton = createRefFor("loginAsUserButton")
        val loginAsAdminButton = createRefFor("loginAsAdminButton")

        constrain(backgroundCard) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(loginNavigator) {
            start.linkTo(parent.start)
            top.linkTo(backgroundCard.bottom, 20.dp)
        }

        constrain(signupNavigator) {
            start.linkTo(loginNavigator.end)
            top.linkTo(loginNavigator.top)
            bottom.linkTo(loginNavigator.bottom)
        }
        
        createHorizontalChain(
            loginNavigator, signupNavigator, 
            chainStyle = ChainStyle.Spread
        )
        
        constrain(phoneNumberInput) {
            top.linkTo(loginNavigator.bottom, 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        
        constrain(passwordInput) {
            top.linkTo(phoneNumberInput.bottom, 20.dp)
            start.linkTo(phoneNumberInput.start)
            end.linkTo(phoneNumberInput.end)
        }
        
        constrain(loginButton) {
            top.linkTo(passwordInput.bottom, 30.dp)
            start.linkTo(phoneNumberInput.start)
            end.linkTo(phoneNumberInput.end)
            width = Dimension.fillToConstraints
        }
        
        constrain(loginAsUserButton) {
            top.linkTo(loginButton.bottom, 10.dp)
        }
        
        constrain(loginAsAdminButton) {
            top.linkTo(loginAsUserButton.top)
            bottom.linkTo(loginAsUserButton.bottom)
        }
        
        createHorizontalChain(
            loginAsUserButton, loginAsAdminButton, 
            chainStyle = ChainStyle.Spread
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}