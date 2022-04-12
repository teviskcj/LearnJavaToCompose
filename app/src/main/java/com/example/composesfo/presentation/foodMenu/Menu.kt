package com.example.composesfo.presentation.foodMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
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
import com.example.composesfo.domain.model.Food
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.navigation.Screen

@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: FoodListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val foodTypeList = foodType()

    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {

            TopAppBarHeader()
            Spacer(modifier = Modifier.padding(10.dp))
            TypeList(foodTypeList, viewModel)
            Spacer(modifier = Modifier.padding(20.dp))
            FoodList(state, navController)

            IsStateError(state)
            IsStateLoading(state)
        }
    }
}

@Composable
fun TopAppBarHeader() {
    Column {
        Text(
            text = "Our",
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp
        )

        Text(
            text = "Menu",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp
        )
    }
}

private fun foodType(): List<String> {
    return listOf("Popular", "Food", "Drink")
}

@Composable
fun TypeList(list: List<String>, viewModel: FoodListViewModel) {

    Row(
        modifier = Modifier
            .padding(0.dp, 8.dp),
    ) {
        list.forEach { text ->
            Column(
                modifier = Modifier
                .padding(0.dp, 4.dp, 12.dp, 4.dp)
                .width(IntrinsicSize.Max)
            ) {
                Text(
                    text = text,
                    color = viewModel.getColor(text),
                    modifier = Modifier
                        .noRippleClickable {
                            viewModel.onTypeChange(text)
                        }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .height(2.dp)
                        .clip(
                            RoundedCornerShape(1.dp)
                        )
                        .background(
                            viewModel.getColor(text)
                        )
                )
            }
        }
    }
}


@Composable
fun FoodList(state: FoodListState, navController: NavController) {
    LazyColumn {
        items(state.foods) { food ->
            FoodListItem(
                food = food,
                onItemClick = {
                    navController.navigate(route = Screen.FoodDetailsScreen.route + "/${food.foodName}")
                }
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
        modifier = Modifier
            .noRippleClickable(onClick = { onItemClick(food) })
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
                    .fillMaxWidth()
            )

            Text(
                text = "RM ${food.foodPrice}.00",
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            Text(
                text = food.foodDescription,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )


            Divider(
                color = Color.LightGray,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
fun IsStateError(state: FoodListState) {
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
}

@Composable
fun IsStateLoading(state: FoodListState) {
    if (state.isLoading) {
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
fun MenuScreenPreview() {
    MenuScreen(navController = rememberNavController())
}