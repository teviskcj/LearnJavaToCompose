package com.example.composesfo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.navigation.Screen
import com.example.composesfo.ui.theme.AllButton

@Composable
fun OrderScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            constraintSet = orderScreenConstraintSet()
        ) {
            Text(
                text = "Current Order",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("currentOrder")
            )

            LazyColumn(
                modifier = Modifier
                    .layoutId("currentList")

            ) {
                items(count = 2){
                    OrderItemCard("xxx", "01-01-2022",0, navController)
                }

            }

            Text(
                text = "Past Order",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("pastOrder")
            )

            LazyColumn(
                modifier = Modifier
                    .layoutId("pastList")

            ) {
                items(count = 2){
                    OrderItemCard("xxx", "01-01-2022",0, navController)
                }

            }
        }
    }
}

@Composable
fun OrderItemCard(orderNo: String, time: String, amount: Int, navController: NavController) {
    Card(backgroundColor = AllButton,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Order No : $orderNo",
                color = Color.White,
                fontSize = 18.sp
            )

            Text(
                text = "Time : $time",
                color = Color.White,
                fontSize = 18.sp
            )
            Text(
                text = "Amount: $amount.00 MYR",
                color = Color.White,
                fontSize = 18.sp
            )

            Button(
                onClick = { navController.navigate(Screen.OrderDetailsScreen.route) },
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            ) {
                Text(
                    text = "View Details",
                    fontSize = 22.sp
                )
            }
        }


    }
}

private fun orderScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val currentOrder =  createRefFor("currentOrder")
        val currentList = createRefFor("currentList")
        val pastOrder = createRefFor("pastOrder")
        val pastList = createRefFor("pastList")

        constrain(currentOrder) {
            top.linkTo(parent.top, 20.dp)
        }

        constrain(currentList) {
            top.linkTo(currentOrder.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(pastOrder) {
            top.linkTo(currentList.bottom, 20.dp)
        }

        constrain(pastList) {
            top.linkTo(pastOrder.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    OrderScreen(navController = rememberNavController())
}