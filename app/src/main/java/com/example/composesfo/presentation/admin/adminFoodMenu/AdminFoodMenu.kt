package com.example.composesfo.presentation.admin.adminFoodMenu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.composesfo.presentation.component.MultiFabState
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun AdminFoodMenuScreen(
    navController: NavController,
    viewModel: AdminFoodMenuViewModel = hiltViewModel()
) {
    val stateCategory = viewModel.stateGetCategories.value
    val categoryList = viewModel.getCategoryList(viewModel.categoryList)
    val state = viewModel.stateFoods.value
    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
    Scaffold(
        topBar = {
            /*TopAppBarWithBackAndAdd(
                onBackClick = {
                    navController.popBackStack()
                },
                text = "Food Menu"
            )*/
            TopAppBar(
                title = {
                    Text(text = "Food Menu")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp,
                actions = {
                    /*IconButton(
                        onClick = {
                            navController.navigate(route = Screen.AdminAddMenuScreen.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = ""
                        )
                    }*/

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = ""
                        )
                    }

                    IconButton(onClick = { viewModel.onShowDropDownMenuChange(true) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more_button),
                            contentDescription = ""
                        )
                    }
                    DropdownMenu(
                        expanded = viewModel.showDropDownMenu,
                        onDismissRequest = { viewModel.onShowDropDownMenuChange(false) }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate(route = Screen.AdminAddFoodScreen.route)
                                viewModel.onShowDropDownMenuChange(false)
                            }
                        ) {
                            Text(text = "Add Food")
                        }
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate(route = Screen.AdminAddCategoryScreen.route)
                                viewModel.onShowDropDownMenuChange(false)
                            }
                        ) {
                            Text(text = "Add Category")
                        }
                        DropdownMenuItem(
                            onClick = {
                                if (stateCategory.categoryList.isNotEmpty()) {
                                    val id = viewModel.getCategoryId(
                                        stateCategory.categoryList,
                                        viewModel.selectedCategory
                                    )
                                    navController.navigate(route = Screen.AdminAddCategoryScreen.route + "/${id}")
                                    viewModel.onShowDropDownMenuChange(false)
                                }
                            }
                        ) {
                            Text(text = "Edit Category")
                        }
                        DropdownMenuItem(
                            onClick = {
                                val id = viewModel.getCategoryId(
                                    stateCategory.categoryList,
                                    viewModel.selectedCategory
                                )
                                viewModel.deleteCartList(id)
                                viewModel.onShowDropDownMenuChange(false)
                                //viewModel.reload()
                            }
                        ) {
                            Text(text = "Delete Category")
                        }
                    }
                }
            )
        },
        backgroundColor = lightgraybg,
        /*floatingActionButton = {
            MultiFloatingActionButton(
                ImageBitmap.imageResource(id = R.drawable.plus),
                listOf(
                    MultiFabItem(
                        "category",
                        ImageBitmap.imageResource(id = R.drawable.plus), "Add Category"
                    ),
                    MultiFabItem(
                        "menu",
                        ImageBitmap.imageResource(id = R.drawable.plus), "Add Menu"
                    )
                ), toState, true, { state ->
                    toState = state
                }
            ) {
                if (it.identifier == "category") {
                    navController.navigate(route = Screen.FoodCategoryMenuScreen.route)
                }

                if (it.identifier == "menu") {
                    navController.navigate(route = Screen.AdminAddMenuScreen.route)
                }
            }
        }*/
    ) {
        Column(
            modifier = Modifier.padding(0.dp)
        ) {
            /*Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    navController.navigate(route = Screen.AdminAddCategoryScreen.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "",
                    tint = white,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Add New Category",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }*/
            /*Spacer(modifier = Modifier.padding(10.dp))
            TypeList(
                list = viewModel.getCategoryList(stateCategory.categoryList),
                getColor = viewModel::getColor,
                onTypeChange = viewModel::onTypeChange
            )
            Spacer(modifier = Modifier.padding(10.dp))*/
            /*if (stateCategory.categoryList.isNotEmpty()) {
                CategoryTab(
                    tabData = viewModel.getCategoryList(stateCategory.categoryList),
                    tabIndex = viewModel.tabIndex,
                    onTabIndexChange = viewModel::onTabIndexChange,
                    onTypeChange = viewModel::onTypeChange
                )
            }*/

            if (categoryList.isNotEmpty()) {
                CategoryTab(
                    tabData = categoryList,
                    tabIndex = viewModel.tabIndex,
                    onTabIndexChange = viewModel::onTabIndexChange,
                    onTypeChange = viewModel::onTypeChange
                )

                val categoryFoodList = viewModel.getCategoryFoodList(
                    viewModel.selectedCategory,
                    stateCategory.categoryList
                )
                FoodList(
                    foods = viewModel.getFoodListByCategory(categoryFoodList, state.foods),
                    navController = navController
                )
            }
        }
        /*val alpha = if (toState == MultiFabState.EXPANDED) 0.4f else 0f
        Box(
            modifier = Modifier
                .alpha(animateFloatAsState(alpha).value)
                .background(black)
                .fillMaxSize()
        )*/
    }

    BackHandler(enabled = viewModel.showDropDownMenu) {
        viewModel.onShowDropDownMenuChange(false)
    }
}

@Composable
fun TypeList(
    list: List<String>,
    getColor: (String) -> Color,
    onTypeChange: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
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
                    color = getColor(text),
                    modifier = Modifier
                        .noRippleClickable {
                            onTypeChange(text)
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
fun FoodList(
    foods: List<Food>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(foods) { food ->
            FoodItem(
                food = food,
                onItemClick = {
                    navController.navigate(route = Screen.AdminEditFoodScreen.route + "/${it.id}")
                }
            )
        }
    }
}

@Composable
fun FoodItem(
    food: Food,
    onItemClick: (Food) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = { onItemClick(food) })
        ) {
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
                    .height(80.dp)
                    .width(120.dp)
                    .padding(vertical = 10.dp, horizontal = 16.dp)
                    .padding(0.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = food.food_name,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "RM ${food.food_price}.00",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        }
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

@Composable
fun CategoryTab(
    tabData: List<String>,
    tabIndex: Int,
    onTabIndexChange: (Int) -> Unit,
    onTypeChange: (String) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier.fillMaxWidth()
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(
                selected = tabIndex == index,
                onClick = {
                    onTabIndexChange(index)
                    onTypeChange(text)
                    //onShowCurrentOrderChange(!showCurrentOrder)
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

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    AdminFoodMenuScreen(navController = rememberNavController())
}