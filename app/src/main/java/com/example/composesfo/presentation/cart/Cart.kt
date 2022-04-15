package com.example.composesfo.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.domain.model.Cart
import com.example.composesfo.presentation.component.TopBarTitle
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.foodDetails.QuantityButton
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.*

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val cartList = state.cartList
    val menuItems = listOf("Edit", "Delete")

    val scaffoldState = rememberScaffoldState()

    for (i in cartList.indices) {
        viewModel.addItem(cartList[i].quantity.toInt())
        viewModel.addTotalAmount(cartList[i].quantity.toInt(),cartList[i].foodPrice.toInt())
    }
    CurrentUserState.totalAmount = viewModel.totalAmount


    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier
            .fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                TopBarTitle(
                    textOne = "Shopping",
                    textTwo = "Cart"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                CartList(
                    cartList = state.cartList,
                    menuItems = menuItems,
                    expanded = viewModel.expanded,
                    onExpandedChange = viewModel::onExpandedChange,
                    navController = navController
                )
                Spacer(modifier = Modifier
                    .padding(20.dp)
                    .weight(1f)
                )
                ButtonWithTotalItems(
                    navController = navController,
                    totalAmount = viewModel.totalAmount,
                    totalItem = viewModel.totalItem
                )
            }
        }
    }




}


@Composable
fun CartList(
    cartList: List<Cart>,
    menuItems: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    navController: NavController
) {
    LazyColumn {
        items(cartList) { cart ->
            CartListItem(
                cart = cart,
                backgroundColor = lightsilverbox,
                menuItems = menuItems,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                navController = navController
            )
        }

    }
}

@Composable
fun CartListItem(
    cart: Cart,
    backgroundColor: Color = Color.Transparent,
    menuItems: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    navController: NavController
) {
    val cartDto = cart

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = cart.foodName,
                fontSize = 18.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                orange,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("RM ")
                        }
                        withStyle(
                            style = SpanStyle(
                                titleTextColor
                            )
                        ) {
                            append("${cart.foodPrice}.00")
                        }
                    },
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier,
                    fontSize = 16.sp

                )
                Box(
                    modifier = Modifier
                        .size(35.dp, 35.dp)
                        .clip(CircleShape)
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "x${cart.quantity}",
                        fontSize = 14.sp,
                        color = titleTextColor
                    )
                }
            }
        }
        IconButton(
            onClick = { onExpandedChange(true) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                null
            )
        }
    }
    DropdownMenu(
        expanded = expanded,
        offset = DpOffset((-40).dp, (40).dp),
        onDismissRequest = { onExpandedChange(false) }) {
        menuItems.forEach {
            DropdownMenuItem(onClick = {
                if (it == "Edit") {
                    navController.navigate(Screen.FoodDetailsScreen.route + "/${cart.foodName}")
                    CurrentUserState.cartFoodId = cart.foodName
                } else {

                }
                onExpandedChange(false)
            }) {
                Text(text = it)
            }
        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
}

@Composable
fun ButtonWithTotalItems(
    navController: NavController,
    totalItem: Int,
    totalAmount: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(color = lightGrey, thickness = 2.dp)
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$totalItem Items",
                fontSize = 14.sp,
                color = lightGrey
            )

            Text(
                text = "RM $totalAmount.00",
                fontSize = 18.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                navController.navigate(Screen.PaymentScreen.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = AllButton),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 30.dp,
                    bottom = 34.dp
                )
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Place Order",
                color = white,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(navController = rememberNavController())
}
