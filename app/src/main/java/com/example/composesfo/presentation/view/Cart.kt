package com.example.composesfo.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton

@Composable
fun CartScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            constraintSet = cartScreenConstraintSet()
        ) {
            Text(
                text = "Total Price = 0.00 MYR",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("totalText")
            )

            LazyColumn(
                modifier = Modifier
                    .layoutId("cartList")

            ) {
                items(count = 3){
                    CartItemCard("Food Name", 0,0)
                }

            }
            Button(
                onClick = { navController.navigate(Screen.PaymentScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("orderButton")
            ) {
                Text(
                    text = "Place Order",
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun CartItemCard(foodName: String, quantity: Int, price: Int) {
        Card(backgroundColor = AllButton, 
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clickable { }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = foodName,
                        color = Color.White,
                        fontSize = 18.sp)

                    Text(
                        text = "Quantity = $quantity",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = "Price: $price.00 MYR",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            
        }
}

private fun cartScreenConstraintSet(): ConstraintSet {
    return ConstraintSet() {
        val totalText =  createRefFor("totalText")
        val cartList = createRefFor("cartList")
        val orderButton = createRefFor("orderButton")

        constrain(totalText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(cartList) {
            top.linkTo(totalText.bottom, 20.dp)
        }

        constrain(orderButton) {
            bottom.linkTo(parent.bottom,20.dp)
            start.linkTo(parent.start, 30.dp)
            end.linkTo(parent.end, 30.dp)
            centerHorizontallyTo(parent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(navController = rememberNavController())
}
