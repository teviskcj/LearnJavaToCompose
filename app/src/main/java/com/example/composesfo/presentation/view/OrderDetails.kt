package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun OrderDetailsScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            constraintSet = orderDetailsConstraintSet()
        ) {
            Text(
                text = "Order Details",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("orderDetails")
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("orderDetailsCard")
            ) {
                OrderDetailsCard("xxx", "01-01-2022")
            }

            Text(
                text = "Customer Info",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("customerInfo")
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("customerInfoCard")
            ) {
                CustomerInfoCard("name", "phone", "address")
            }

            Text(
                text = "Food Details",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("foodDetails")
            )

            LazyColumn(
                modifier = Modifier
                    .layoutId("foodDetailsCard")

            ) {
                items(count = 2){
                    FoodsDetailsCard("name", 1, 10)
                }

            }

            Text(
                text = "Total Amount",
                fontSize = 15.sp,
                modifier = Modifier
                    .layoutId("totalAmount")
            )

            Text(
                text = "RM 20.00",
                fontSize = 15.sp,
                modifier = Modifier
                    .layoutId("intTotal")
            )
        }
    }
}

@Composable
fun OrderDetailsCard(orderNo: String, time: String) {
    Column(
            modifier = Modifier.padding(10.dp)
            ) {
        Text(
            text = "Order No : $orderNo",
            fontSize = 18.sp
        )

        Text(
            text = "Time : $time",
            fontSize = 18.sp
        )
    }
}

@Composable
fun CustomerInfoCard(name: String, phone: String, address: String) {
    Column(
            modifier = Modifier.padding(10.dp)
            ) {
        Text(
            text = name,
            fontSize = 18.sp
        )

        Text(
            text = phone,
            fontSize = 18.sp
        )

        Text(
            text = address,
            fontSize = 18.sp
        )
    }
}

@Composable
fun FoodsDetailsCard(foodName: String, quantity: Int, price: Int) {
    Column(
            modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "$quantity",
                fontSize = 15.sp
            )

            Text(
                text = foodName,
                fontSize = 15.sp
            )

            Text(
                text = "RM $price.00",
                fontSize = 15.sp
            )
        }
    }
}

private fun orderDetailsConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val orderDetails =  createRefFor("orderDetails")
        val orderDetailsCard = createRefFor("orderDetailsCard")
        val customerInfo = createRefFor("customerInfo")
        val customerInfoCard = createRefFor("customerInfoCard")
        val foodDetails = createRefFor("foodDetails")
        val foodDetailsCard = createRefFor("foodDetailsCard")
        val totalAmount = createRefFor("totalAmount")
        val intTotal = createRefFor("intTotal")

        constrain(orderDetails) {
            top.linkTo(parent.top, 20.dp)
        }

        constrain(orderDetailsCard) {
            top.linkTo(orderDetails.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(customerInfo) {
            top.linkTo(orderDetailsCard.bottom, 20.dp)
        }

        constrain(customerInfoCard) {
            top.linkTo(customerInfo.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(foodDetails) {
            top.linkTo(customerInfoCard.bottom, 20.dp)
        }

        constrain(foodDetailsCard) {
            top.linkTo(foodDetails.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(totalAmount) {
            top.linkTo(foodDetailsCard.bottom, 5.dp)
        }

        constrain(intTotal) {
            top.linkTo(totalAmount.top)
            end.linkTo(parent.end)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailsScreenPreview() {
    OrderDetailsScreen(navController = rememberNavController())
}