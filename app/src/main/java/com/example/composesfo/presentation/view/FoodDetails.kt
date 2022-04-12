package com.example.composesfo.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.composesfo.R
import com.example.composesfo.presentation.foodDetails.FoodDetailsViewModel
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton

@Composable
fun FoodDetailsScreen(
    navController: NavController,
    viewModel: FoodDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            constraintSet = foodDetailsScreenConstraintSet()
        ) {
            state.food?.let { food ->
                val painter = rememberImagePainter(
                    data = food.foodImage,
                    builder = {
                        placeholder(R.drawable.ic_image_placeholder)
                        crossfade(500)
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = "Food Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .padding(0.dp)
                        .layoutId("imageFood")
                )

                Text(
                    text = food.foodName,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .layoutId("textFood")
                )

                Text(
                    text = food.foodDescription,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .layoutId("textDescription")
                )

                Text(
                    text = food.foodPrice,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .layoutId("textPrice")
                )
            }

            /*Image(
                painter = painterResource(
                    id = R.drawable.ic_launcher_background
                ),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .padding(0.dp)
                    .layoutId("imageFood")
            )

            Text(
                text = "Food Name",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .layoutId("textFood")
            )

            Text(
                text = "Description",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .layoutId("textDescription")
            )

            Text(
                text = "0.00 MYR",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .layoutId("textPrice")
            )*/

            var numberValue: Int by rememberSaveable { mutableStateOf(1) }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(AllButton)
                    .layoutId("quantityButton")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { if (numberValue>1) numberValue -= 1 }
                    ) {
                        Text(
                            text = "-",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }

                    Text(
                        text = "$numberValue",
                        color = Color.White,
                        fontSize = 24.sp
                    )

                    TextButton(
                        onClick = { numberValue += 1 }
                    ) {
                        Text(
                            text = "+",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                }
            }


            Button(
                onClick = {
                    navController.navigate(Screen.HomeScreen.route) {
                        navController.backQueue.clear()
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("addButton")
            ) {
                Text(
                    text = "Add To Cart",
                    fontSize = 22.sp
                )
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

private fun foodDetailsScreenConstraintSet(): ConstraintSet {
    return ConstraintSet{
        val imageFood = createRefFor("imageFood")
        val textFood = createRefFor("textFood")
        val textDescription = createRefFor("textDescription")
        val textPrice = createRefFor("textPrice")
        val quantityButton = createRefFor("quantityButton")
        val addButton = createRefFor("addButton")

        constrain(imageFood) {
            top.linkTo(parent.top, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }

        constrain(textFood) {
            top.linkTo(imageFood.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(textDescription) {
            top.linkTo(textFood.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(textPrice) {
            top.linkTo(textDescription.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(quantityButton) {
            bottom.linkTo(addButton.top, 50.dp)
            centerHorizontallyTo(parent)
        }

        constrain(addButton) {
            bottom.linkTo(parent.bottom, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailsScreenPreview() {
    FoodDetailsScreen(navController = rememberNavController())
}