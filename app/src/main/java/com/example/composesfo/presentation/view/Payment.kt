package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.data.remote.dto.OrderDto
import com.example.composesfo.presentation.component.getDate
import com.example.composesfo.presentation.component.getTime
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.payment.OrderViewModel
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val state = viewModel.stateCart.value

    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            OrderForm(
                name = viewModel.name,
                phone = viewModel.phone,
                address = viewModel.address,
                city = viewModel.city,
                onNameChange = viewModel::onNameChange,
                onPhoneChange = viewModel::onPhoneChange,
                onAddressChange = viewModel::onAddressChange,
                onCityChange = viewModel::onCityChange
            )
            Spacer(modifier = Modifier
                .padding(20.dp)
                .weight(1f)
            )

            Button(
                onClick = {
                    if (viewModel.checkNullField()) {
                        val orderId = viewModel.createOrderId()
                        val orderDto = OrderDto(
                            address = viewModel.address,
                            city = viewModel.city,
                            date = getDate(),
                            time = getTime(),
                            longitude = 0.0,
                            latitude = 0.0,
                            name = viewModel.name,
                            orderID = orderId,
                            phone = viewModel.phone,
                            state = "P",
                            totalAmount = ""
                        )

                        viewModel.createOrder(
                            userId = CurrentUserState.userId,
                            orderId = orderId,
                            orderDto = orderDto
                        )

                        for (cart in state.cartList) {
                            val cartDto = CartDto(
                                foodName = cart.foodName,
                                foodPrice = cart.foodPrice,
                                quantity = cart.quantity
                            )

                            viewModel.createCartOrder(
                                userId = CurrentUserState.userId,
                                orderId = orderId,
                                foodId = cart.foodName,
                                cartDto = cartDto
                            )

                            viewModel.deleteCartList(CurrentUserState.userId)
                        }
                    }

                    if (viewModel.isOrderSuccessful(viewModel.stateOrder.value.error)) {
                        navController.navigate(Screen.HomeScreen.route){
                            navController.backQueue.clear()
                        }
                    }
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
                    text = "PROCEED TO PAYMENT",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun OrderForm(
    name: String,
    phone: String,
    address: String,
    city: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onCityChange: (String) -> Unit
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = { stringResource(R.string.your_name) },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { onNameChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { onPhoneChange(it) },
            label = { stringResource(R.string.your_phone) },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { onPhoneChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = address,
            onValueChange = { onAddressChange(it) },
            label = { stringResource(R.string.your_address) },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { onAddressChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = city,
            onValueChange = { onCityChange(it) },
            label = { stringResource(R.string.your_address) },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { onCityChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                } },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(navController = rememberNavController())
}