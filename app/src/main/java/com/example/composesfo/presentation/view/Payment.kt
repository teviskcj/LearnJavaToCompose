package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.presentation.navigation.Screen

@Composable
fun PaymentScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            constraintSet = paymentScreenConstraintSet()
        ) {

            val inputName = remember { mutableStateOf("") }
            val inputPhone = remember { mutableStateOf("") }
            val inputAddress = remember { mutableStateOf("") }
            OutlinedTextField(
                value = inputName.value,
                onValueChange = { inputName.value = it },
                label = { stringResource(R.string.your_name) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("inputName")
            )

            OutlinedTextField(
                value = inputPhone.value,
                onValueChange = { inputPhone.value = it },
                label = { stringResource(R.string.your_phone) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("inputPhone")
            )

            OutlinedTextField(
                value = inputAddress.value,
                onValueChange = { inputAddress.value = it },
                label = { stringResource(R.string.your_address) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("inputAddress")
            )

            Button(
                onClick = {
                    navController.navigate(Screen.HomeScreen.route){
                        navController.backQueue.clear()
                    }
                          },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("setButton")
            ) {
                Text(
                    text = "PROCEED TO PAYMENT",
                    fontSize = 22.sp
                )
            }
        }
    }
}

private fun paymentScreenConstraintSet(): ConstraintSet {
    return ConstraintSet{
        val inputName = createRefFor("inputName")
        val inputPhone = createRefFor("inputPhone")
        val inputAddress = createRefFor("inputAddress")
        val confirmButton = createRefFor("setButton")

        constrain(inputName) {
            top.linkTo(parent.top, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(inputPhone) {
            top.linkTo(inputName.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(inputAddress) {
            top.linkTo(inputPhone.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(confirmButton) {
            bottom.linkTo(parent.bottom, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(navController = rememberNavController())
}