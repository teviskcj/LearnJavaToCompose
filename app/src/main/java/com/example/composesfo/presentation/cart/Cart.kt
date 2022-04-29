package com.example.composesfo.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.domain.model.Cart
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.*

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val cartList = state.cartList
    var totalAmount =0
    var totalItem =0

    val scaffoldState = rememberScaffoldState()


    for (i in cartList.indices) {
        val quantity = viewModel.addItem(cartList[i].quantity.toInt())
        val subtotal = viewModel.addTotalAmount(cartList[i].quantity.toInt(),cartList[i].foodPrice.toInt())
        totalItem += quantity
        totalAmount += subtotal
    }

    if (viewModel.openDialog) {
        ConfirmDialog(
            onConfirmClicked = {
                viewModel.deleteCartList(CurrentUserState.userId)
                viewModel.onOpenDialogChange(false)
            }, onDismiss =  {
                viewModel.onOpenDialogChange(false)
            }
        )
    }


    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier
            .fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                TopBarWithDeleteCart(
                    textOne = "Shopping",
                    textTwo = "Cart",
                    painter = painterResource(id = R.drawable.ic_delete_cart),
                    onOpenDialogChange = viewModel::onOpenDialogChange
                )
                Spacer(modifier = Modifier.padding(10.dp))
                CartList(
                    cartList = viewModel.cartList,
                    navController = navController,
                    onItemDelete = viewModel::deleteCartItem
                )
                Spacer(modifier = Modifier
                    .padding(20.dp)
                    .weight(1f)
                )
                ButtonWithTotalItems(
                    navController = navController,
                    totalAmount = totalAmount,
                    totalItem = totalItem
                )
            }
        }
    }




}

@Composable
fun TopBarWithDeleteCart(
    textOne: String,
    textTwo: String,
    painter: Painter,
    onOpenDialogChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Text(
                text = textOne,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp
            )

            Text(
                text = textTwo,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp
            )
        }

        IconButton(
            onClick = {
                onOpenDialogChange(true)
            }
        ) {
            Icon(
                painter = painter,
                contentDescription = stringResource(R.string.delete_all_cart),
                tint = AllButton
            )
        }
    }

}

@Composable
fun CartList(
    cartList: List<Cart>,
    navController: NavController,
    onItemDelete: (String, String) -> Unit
) {
    LazyColumn {
        items(cartList) { cart ->
            CartListItem(
                cart = cart,
                backgroundColor = lightsilverbox,
                navController = navController,
                deleteCartItem = (onItemDelete)
            )
        }

    }
}

@Composable
fun CartListItem(
    cart: Cart,
    backgroundColor: Color = Color.Transparent,
    navController: NavController,
    deleteCartItem: (String, String) -> Unit
) {
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
            onClick = {
                navController.navigate(Screen.FoodDetailsScreen.route + "/${cart.foodName}")
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(R.string.edit_cart_item),
                tint = AllButton
            )
        }

        IconButton(
            onClick = {
                deleteCartItem(CurrentUserState.userId, cart.foodName)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(R.string.delete_cart_item),
                tint = AllButton
            )
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
                text = "$totalItem " + stringResource(R.string.items),
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
                CurrentUserState.totalAmount = totalAmount
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
                text = stringResource(R.string.place_order),
                color = white,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

    }
}

@Composable
fun ConfirmDialog(
    onConfirmClicked: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // TITLE
                Text(text = stringResource(R.string.delete_shopping_cart), style = MaterialTheme.typography.subtitle1)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f, fill = false)
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.are_you_sure_to_delete_all_cart_items),
                        style = MaterialTheme.typography.body2
                    )
                }

                // BUTTONS
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    TextButton(onClick = { onConfirmClicked(CurrentUserState.userId) }) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(navController = rememberNavController())
}
