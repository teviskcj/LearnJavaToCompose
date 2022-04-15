package com.example.composesfo.presentation.foodDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.StoreUserPhone
import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.domain.model.Food
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton

@Composable
fun FoodDetailsScreen(
    navController: NavController,
    viewModel: FoodDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val food = state.food

    val context = LocalContext.current
    val dataStore = StoreUserPhone(context)
    val userPhone = dataStore.getPhone.collectAsState(initial = "")
    val userId = userPhone.value!!

    val backStackRoute = navController.previousBackStackEntry?.destination?.route

    

    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = CurrentUserState.cartFoodId)

            food?.let {
                DisplayFoodDetail(food = food)
            }
            Spacer(modifier = Modifier
                .padding(10.dp)
                .weight(1f))
            QuantityButton(
                stateQuantity = viewModel.stateQuantity,
                decreaseQuantity = viewModel::decreaseQuantity,
                increaseQuantity = viewModel::increaseQuantity
            )
            Spacer(modifier = Modifier
                .padding(20.dp)
                .weight(1f))
            Button(
                onClick = {
                    val cartDto = food?.let {
                        CartDto(
                            foodName = food.foodName,
                            foodPrice = food.foodPrice,
                            quantity = viewModel.stateQuantity.toString()
                        )
                    }

                    if (cartDto != null) {
                        viewModel.createCart(CurrentUserState.userId, cartDto.foodName, cartDto)
                    }

                    if (viewModel.isCartAdded(state.error)) {
                        navController.navigate(Screen.HomeScreen.route) {
                            navController.backQueue.clear()
                        }
                    }
                },
                modifier = Modifier
                    .height(60.dp)
            ) {
                Text(
                    text = "Add To Cart",
                    fontSize = 22.sp
                )
            }

            IsStateError(state.error)
            IsStateLoading(state.isLoading)
        }
    }
}


@Composable
fun DisplayFoodDetail(food: Food) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberImagePainter(
            data = food.foodImage,
            builder = {
                crossfade(500)
            }
        )
        Image(
            painter = painter,
            contentDescription = "Food Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 30.dp)
                .fillMaxWidth()
                .height(200.dp)
        )

        Text(
            text = food.foodName,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = food.foodDescription,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )

        Text(
            text = "RM ${food.foodPrice}.00",
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }

}

@Composable
fun QuantityButton(
    stateQuantity: Int,
    decreaseQuantity: () -> Unit,
    increaseQuantity: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(AllButton)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(20.dp, 8.dp)
        ) {
            Text(
                text = "-",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.noRippleClickable { decreaseQuantity() }
            )

            Text(
                text = "$stateQuantity",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .width(30.dp)
            )

            Text(
                text = "+",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.noRippleClickable { increaseQuantity() }
            )

        }
    }
}

@Composable
fun IsStateError(error: String) {
    if (error.isNotBlank()) {
        Text(
            text = error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun IsStateLoading(isLoading: Boolean) {
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailsScreenPreview() {
    FoodDetailsScreen(navController = rememberNavController())
}