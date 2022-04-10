package com.example.composesfo.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.composesfo.R
import com.example.composesfo.domain.model.Food
import com.example.composesfo.presentation.foodMenu.FoodListViewModel
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton

@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: FoodListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val foodTypeList = foodType()

    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
            constraintSet = menuScreenConstraintSet()
        ) {
            Text(
                text = "Food Menu",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.layoutId("menuText")
            )

            LazyRow(
                modifier = Modifier.layoutId("typeList"),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = foodTypeList) { item ->
                    Text(text = item, modifier = Modifier
                        .clickable {}
                        .padding(start = 10.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                        .border(1.dp, AllButton)
                    )
                }
            }

            val list = listOf(1,2,3,4,5,6,7,8,9)
            /*LazyColumn(
                modifier = Modifier
                    .layoutId("foodList")

            ) {
                items(items = list) { item ->
                        FoodsItem(item, navController)
                }

            }*/

            LazyColumn(
                modifier = Modifier
                    .layoutId("foodList")

            ) {
                items(state.foods) { food ->
                    FoodListItem(
                        food = food,
                        onItemClick = {
                            navController.navigate(route = Screen.FoodDetailsScreen.route + "/${food.foodName}")
                        }
                    )
                }

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

private fun foodType(): List<String> {
    return listOf("Popular", "Food", "Drink")
}

@Composable
fun FoodsItem(i: Int, navController: NavController) {
    Card(
        modifier = Modifier.clickable(
            onClick = { navController.navigate(route = Screen.FoodDetailsScreen.route) }
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Food Name $i",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(
                    id = R.drawable.ic_launcher_background
                ),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .padding(0.dp)
            )

            Text(
                text = "0.00 MYR",
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            Text(
                text = "Description",
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )


            Divider(
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
fun FoodListItem(
    food: Food,
    onItemClick: (Food) -> Unit
) {
    Row(
        modifier = Modifier.clickable(
            onClick = { onItemClick(food) }
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = food.foodName,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(
                    id = R.drawable.ic_launcher_background
                ),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .padding(0.dp)
            )

            Text(
                text = food.foodPrice,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            Text(
                text = food.foodDescription,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )


            Divider(
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

private fun menuScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val menuText = createRefFor("menuText")
        val typeList = createRefFor("typeList")
        val foodList = createRefFor("foodList")

        constrain(menuText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(typeList) {
            top.linkTo(menuText.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(foodList) {
            top.linkTo(typeList.bottom, 20.dp)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen(navController = rememberNavController())
}