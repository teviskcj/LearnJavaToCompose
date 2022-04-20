package com.example.composesfo.presentation.orderDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.domain.model.Cart
import com.example.composesfo.domain.model.OrderDetail
import com.example.composesfo.presentation.component.TopBarTitle
import com.example.composesfo.presentation.ui.theme.lightblack
import com.example.composesfo.presentation.ui.theme.titleTextColor

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val stateCart = viewModel.stateCart.value
    val orderDetail = OrderDetail("","","","","","","","","")
    Box(modifier = Modifier
        .fillMaxSize()) {
        Column {
            Box(modifier = Modifier.padding(16.dp)) {
                TopBarTitle(textOne = "Order", textTwo = "Details")
            }
            state.orderDetail?.let {
                Spacer(modifier = Modifier.padding(10.dp))
                OrderDetailsCard(orderDetail = it)
                Spacer(modifier = Modifier.padding(10.dp))
                CustomerInfoCard(orderDetail = it)
                Spacer(modifier = Modifier.padding(10.dp))
                FoodsDetailsCard(
                    orderDetail = it,
                    cartList = stateCart.cartList
                )
            }

        }
    }
    /*Surface(color = Color.White) {
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
                //OrderDetailsCard("xxx", "01-01-2022")
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
                //CustomerInfoCard("name", "phone", "address")
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
    }*/
}

@Composable
fun OrderDetailsCard(
    orderDetail: OrderDetail
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Order Details",
                fontSize = 20.sp
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = lightblack,
                            fontSize = 18.sp
                        )
                    ) {
                        append("Order No : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            titleTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append(orderDetail.orderID)
                    }
                }
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = lightblack,
                            fontSize = 18.sp
                        )
                    ) {
                        append("Time : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            titleTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append("${orderDetail.date}, ${orderDetail.time}")
                    }
                }
            )
        }
    }
}

@Composable
fun CustomerInfoCard(
    orderDetail: OrderDetail
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Customer Info",
                fontSize = 20.sp
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = lightblack,
                            fontSize = 18.sp
                        )
                    ) {
                        append("Name : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            titleTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append(orderDetail.name)
                    }
                }
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = lightblack,
                            fontSize = 18.sp
                        )
                    ) {
                        append("Phone : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            titleTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append(orderDetail.phone)
                    }
                }
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = lightblack,
                            fontSize = 18.sp
                        )
                    ) {
                        append("Address : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            titleTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append(orderDetail.address)
                    }
                }
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = lightblack,
                            fontSize = 18.sp
                        )
                    ) {
                        append("City : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            titleTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append(orderDetail.city)
                    }
                }
            )
        }
    }
}

@Composable
fun FoodsDetailsCard(
    orderDetail: OrderDetail,
    cartList: List<Cart>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Food Details",
                fontSize = 20.sp
            )

            LazyColumn {
                items(cartList) { cart ->
                    OrderCartItem(cart = cart)
                }
            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Total Amount",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "RM ${orderDetail.totalAmount}.00",
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun OrderCartItem(
    cart: Cart
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "${cart.quantity}x",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 5.dp, end = 10.dp)
        )

        Text(
            text = cart.foodName,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "RM ${cart.foodPrice}.00",
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailsScreenPreview() {
    OrderDetailsScreen(navController = rememberNavController())
}