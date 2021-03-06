package com.example.composesfo.presentation.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.composesfo.R
import com.example.composesfo.domain.model.OrderView
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.*

@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier
        .fillMaxSize()) {
        Column {
            /*TopBarTitle(
                textOne = "Current",
                textTwo = "Order"
            )*/
            //Spacer(modifier = Modifier.padding(10.dp))
            OrderTab(
                tabIndex = viewModel.tabIndex,
                onTabIndexChange = viewModel::onTabIndexChange,
                showCurrentOrder = viewModel.showCurrentOrder,
                onShowCurrentOrderChange = viewModel::onShowCurrentOrderChange
            )
            Spacer(modifier = Modifier.padding(10.dp))
            if (viewModel.showCurrentOrder) {
                OrderList(
                    orderList = viewModel.currentOrderList,
                    navController = navController,
                    getStateColor = viewModel::getStateColor,
                    getStateName = viewModel::getStateName
                )
            }
            if (!viewModel.showCurrentOrder) {
                OrderList(
                    orderList = viewModel.passOrderList,
                    navController = navController,
                    getStateColor = viewModel::getStateColor,
                    getStateName = viewModel::getStateName
                )
            }
        }
    }


}

@Composable
fun OrderTab(
    tabIndex: Int,
    onTabIndexChange: (Int) -> Unit,
    showCurrentOrder: Boolean,
    onShowCurrentOrderChange: (Boolean) -> Unit
) {
    val tabData = listOf(
        stringResource(R.string.current_order),
        stringResource(R.string.pass_order),
    )
    TabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = AllButton,
        modifier = Modifier.fillMaxWidth()
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(
                selected = tabIndex == index,
                onClick = {
                    onTabIndexChange(index)
                    onShowCurrentOrderChange(!showCurrentOrder)
                },
                text = {
                    Text(
                        text = text,
                        color = white,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    )
                },
            )
        }
    }
}

@Composable
fun OrderList(
    orderList: List<OrderView>,
    navController: NavController,
    getStateColor: (String) -> Color,
    getStateName: (String) -> String
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(orderList) { orderView ->
            OrderItemCard(
                navController = navController,
                orderView = orderView,
                getStateColor = getStateColor,
                getStateName = getStateName
            )
        }
    }
}

@Composable
fun OrderItemCard(
    navController: NavController,
    orderView: OrderView,
    getStateColor: (String) -> Color,
    getStateName: (String) -> String
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { navController.navigate(route = Screen.OrderDetailsScreen.route + "/${orderView.orderID}") }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .size(100.dp, 100.dp)
                        .padding(end = 10.dp, bottom = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sample_food),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = lightblack,
                                fontSize = 18.sp
                            )
                        ) {
                            append(stringResource(R.string.order_no) + " : \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                titleTextColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append(orderView.orderID)
                        }
                    }
                )

                Text(
                    text = getStateName(orderView.state),
                    color = getStateColor(orderView.state)
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${orderView.date}, ${orderView.time}",
                    color = subTitleTextColor
                )

                Text(
                    text = "RM ${orderView.totalAmount}.00",
                    fontSize = 22.sp
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    OrderScreen(navController = rememberNavController())
}