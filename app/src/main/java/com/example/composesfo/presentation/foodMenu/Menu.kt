package com.example.composesfo.presentation.foodMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.domain.model.Food
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.frameColor
import kotlinx.coroutines.CoroutineScope
import kotlin.math.roundToInt

@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: FoodListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val stateCategory = viewModel.stateCategory.value
    val categoryList = viewModel.getCategoryList(viewModel.categoryList)
    val categoryFoodList = viewModel.getCategoryFoodList(
        viewModel.stateType,
        stateCategory.categoryList
    )

    val foodTypeList = viewModel.stateList
    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (scrollState.isScrollInProgress) {
        viewModel.changeTypeByScrollPosition(
            currentValue = scrollState.value,
            maxValue = scrollState.maxValue
        )
    }

    if (listState.isScrollInProgress) {
        viewModel.changeTypeByScrollPosition(
            currentValue = listState.firstVisibleItemScrollOffset,
            maxValue = scrollState.maxValue
        )
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(frameColor)
    ) {
        Column {

            TopAppBarHeader()
            Spacer(modifier = Modifier.padding(10.dp))
            TypeList(
                list = categoryList,
                getColor = viewModel::getColor,
                onTypeChange = viewModel::onTypeChange,
                scrollState = scrollState,
                coroutineScope = coroutineScope,
                scrollToType = viewModel::scrollToByType,
                listState = listState,
                scrollToCategory = viewModel::scrollToByCategory
            )
            Spacer(modifier = Modifier.padding(20.dp))
            FoodListByType(
                types = foodTypeList,
                categoryList = stateCategory.categoryList,
                foods = state.foods,
                navController = navController,
                getFoodListByType = viewModel::getFoodListByType,
                getFoodListByCategory = viewModel::getFoodListByCategory,
                scrollState = scrollState,
                checkType = viewModel::addTypePositionList,
                listState = listState
            )

            IsStateError(state.error)
            IsStateLoading(state.isLoading)
        }
    }
}

@Composable
fun TopAppBarHeader() {
    Column(modifier = Modifier.padding(16.dp)) {
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

@Composable
fun TypeList(
    list: List<String>,
    getColor: (String) -> Color,
    onTypeChange: (String) -> Unit,
    scrollState: ScrollState,
    coroutineScope: CoroutineScope,
    scrollToType: (CoroutineScope,ScrollState) -> Unit,
    listState: LazyListState,
    scrollToCategory: (CoroutineScope,LazyListState) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
    ) {
        list.forEach { text ->
            Column(
                modifier = Modifier
                    .padding(0.dp, 4.dp, 12.dp, 4.dp)
                    .width(IntrinsicSize.Max)
            ) {
                Text(
                    text = text,
                    color = getColor(text),
                    modifier = Modifier
                        .noRippleClickable {
                            onTypeChange(text)
                            //scrollToType(coroutineScope,scrollState)
                            scrollToCategory(coroutineScope,listState)
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
                            getColor(text)
                        )
                )
            }
        }
    }
}

@Composable
fun FoodListByType(
    types: List<String>,
    categoryList: List<FoodCategoryDto>,
    foods: List<Food>,
    navController: NavController,
    getFoodListByType: (String, List<Food>) -> List<Food>,
    getFoodListByCategory: (List<String>, List<Food>) -> List<Food>,
    scrollState: ScrollState,
    checkType: (String,Int) -> Unit,
    listState: LazyListState
) {
    LazyColumn(state = listState) {
        items(categoryList, key = { it }) { category ->
            FoodList(
                foods = getFoodListByCategory(category.foods, foods),
                navController = navController,
                type = category.category_name,
                category = category,
                checkType = checkType
            )
        }
        itemsIndexed(items = categoryList, itemContent = { index, category ->



            FoodList(
                foods = getFoodListByCategory(category.foods, foods),
                navController = navController,
                type = category.category_name,
                category = category,
                checkType = checkType
            )
        })
        categoryList.forEach {
            item {
                FoodList(
                    foods = getFoodListByCategory(it.foods, foods),
                    navController = navController,
                    type = it.category_name,
                    category = it,
                    checkType = checkType
                )
            }

        }
    }
    /*Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        categoryList.forEach {
            //val list = getFoodListByType(it, foods)
            FoodList(
                foods = getFoodListByCategory(it.foods, foods),
                navController = navController,
                type = it.category_name,
                category = it,
                checkType = checkType
            )
        }

        *//*types.forEach {
            val list = getFoodListByType(it, foods)
            FoodList(
                foods = list,
                navController = navController,
                type = it,
                checkType = checkType
            )
        }*//*
    }*/
}

@Composable
fun FoodList(
    foods: List<Food>,
    navController: NavController,
    type: String,
    category: FoodCategoryDto,
    checkType: (String,Int) -> Unit
) {

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp)
                .onGloballyPositioned { layoutCoordinates ->
                    checkType(
                        category.category_name,
                        layoutCoordinates.positionInRoot().y.roundToInt()
                    )
                },
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = category.category_name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = category.category_description,
                fontSize = 15.sp
            )

            foods.forEach { food ->
                FoodListItem(
                    food = food,
                    onItemClick = {
                        navController.navigate(route = Screen.FoodDetailsScreen.route + "/${it.food_name}")
                    }
                )
            }
        }
        /*Column {
            when (type) {
                "Popular" -> PopularListHeader(
                    type = type,
                    description = "Most ordered right now."
                )
                "Food" -> FoodListHeader(
                    type = type,
                    description = "Chicken Chop, Spaghetti...",
                    checkType = checkType
                )
                "Drink" -> FoodListHeader(
                    type = type,
                    description = "Soda Drink, Fruit Drink",
                    checkType = checkType
                )
            }
            foods.forEach { food ->
                FoodListItem(
                    food = food,
                    onItemClick = {
                        navController.navigate(route = Screen.FoodDetailsScreen.route + "/${it.food_name}")
                    }
                )
            }
        }*/
    }
    Spacer(modifier = Modifier.padding(10.dp))
}

@Composable
fun PopularListHeader(
    type: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_popular),
                contentDescription = null,
                tint = AllButton
            )

            Text(
                text = type,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = description,
            fontSize = 15.sp
        )
    }
}

@Composable
fun FoodListHeader(
    type: String,
    description: String,
    checkType: (String,Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .onGloballyPositioned { layoutCoordinates ->
                checkType(type, layoutCoordinates.positionInRoot().y.toInt())
            },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = type,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = description,
            fontSize = 15.sp
        )
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
                text = food.food_name,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )

            val painter = rememberImagePainter(
                data = food.food_image_url,
                builder = {
                    //placeholder(R.drawable.ic_image_placeholder)
                    crossfade(500)
                }
            )
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.food_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .padding(vertical = 10.dp, horizontal = 16.dp)
                    .padding(0.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "RM ${food.food_price}.00",
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            Text(
                text = food.food_description,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )


            Divider(
                color = Color.LightGray,
                modifier = Modifier.padding(
                    top = 10.dp,
                    start = 16.dp,
                    end = 16.dp
                )
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
fun MenuScreenPreview() {
    MenuScreen(navController = rememberNavController())
}