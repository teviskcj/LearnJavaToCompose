package com.example.composesfo.presentation.admin.adminAddCategory

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.presentation.component.ExposedDropMenuStateHolder
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.component.rememberExposedMenuStateHolder
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.orange
import com.example.composesfo.presentation.ui.theme.white
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun AdminAddCategoryScreen(
    navController: NavController,
    viewModel: AdminAddCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.stateGetCategories.value
    val stateFoods = viewModel.stateFoods
    val stateGetCategory = viewModel.stateGetCategory
    val stateHolder = rememberExposedMenuStateHolder()
    val selectedFoodList = viewModel.selectedFoodList
    val dropDownItemList = viewModel.getFoodList(stateFoods.value.foods)

    stateGetCategory.value.category?.run {
        viewModel.onCategoryChange(category_name)
        viewModel.onDescriptionChange(category_description)

        if (state.categoryList.isNotEmpty()) {
            viewModel.addExistingFoodList(categoryList = viewModel.foodList, foods = foods)
            /*for (index in 0..foods.lastIndex) {
                for (i in 0 until viewModel.foodList.lastIndex) {
                    if (foods[index] == viewModel.foodList[i]) {
                        viewModel.addSelectedFoodList(foods[index])
                        viewModel.removeFoodList(foods[index])
                        //break
                    }
                }
                //Log.d("testing123", "index now is : $index")
                *//*if (foods[index] == viewModel.foodList[index]) {
                    viewModel.addSelectedFoodList(foods[index])
                    viewModel.removeFoodList(foods[index])
                    //break
                }*//*
            }*/
            /*it.foods.forEachIndexed { index, foodName ->
                if (it.foods[index] == state.categoryList[index].id && index < it.foods.size) {
                    viewModel.addSelectedFoodList(foodName)
                    viewModel.removeFoodList(foodName)
                }
            }*/
        }

    }

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
                    Text(text = "Add Category")
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
                elevation = 10.dp
            )
        },
        backgroundColor = lightgraybg
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),

            ) {
                TextFieldWithNoIcon(
                    text = viewModel.category,
                    onTextChange = viewModel::onCategoryChange,
                    label = "Category",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(10.dp))

                TextFieldWithNoIcon(
                    text = viewModel.description,
                    onTextChange = viewModel::onDescriptionChange,
                    label = "Description",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(10.dp))

                FoodsDropdownList(
                    stateHolder = stateHolder,
                    foods = viewModel.foodList,
                    addSelectedFoodList = viewModel::addSelectedFoodList,
                    removeFoodList = viewModel::removeFoodList
                )

                Spacer(modifier = Modifier.padding(10.dp))


                Text(
                    text = "Foods Selected : ",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(10.dp))
                
                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (viewModel.selectedFoodList.isNotEmpty()) {
                        viewModel.selectedFoodList.forEach {
                            //Text(text = it)
                            FoodTag(
                                tag = it,
                                removeItem = viewModel::removeSelectedFoodItem,
                                addDropDownItem = viewModel::addFoodItem
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    Spacer(modifier = Modifier.padding(20.dp))

                    Button(
                        onClick = {
                            state.categoryList.let {
                                //val id = viewModel.createCategoryId(it.size)
                                var id = viewModel.getCategoryId(it)
                                val list = viewModel.selectedFoodList.toList()

                                if (stateGetCategory.value.category != null) {
                                    id = stateGetCategory.value.category!!.id
                                }

                                val foodCategoryDto = FoodCategoryDto(
                                    id = id,
                                    category_name = viewModel.category,
                                    category_description = viewModel.description,
                                    foods = viewModel.getSelectedFoodList(list)
                                )

                                viewModel.createCategory(id, foodCategoryDto)
                                navController.navigate(route = Screen.AdminFoodMenuScreen.route) {
                                    popUpTo(Screen.AdminHomeScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 30.dp,
                                bottom = 34.dp
                            ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Add Category",
                            color = white,
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }
                }





            }
        }
    }
    /*BackHandler(enabled = true) {
        navController.navigate(route = Screen.AdminFoodMenuScreen.route) {
            popUpTo(Screen.AdminHomeScreen.route) {
                inclusive = true
            }
        }
    }*/
}

@Composable
fun FoodsDropdownList(
    stateHolder: ExposedDropMenuStateHolder,
    foods: List<String>,
    addSelectedFoodList: (String) -> Unit,
    removeFoodList: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = stateHolder.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = stateHolder.icon),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            stateHolder.onEnabledChange(!stateHolder.enabled)
                        }
                    )
                },
                modifier = Modifier
                    .onGloballyPositioned {
                        stateHolder.onSizeChange(it.size.toSize())
                    }
                    .fillMaxWidth()
            )

            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabledChange(false)
                },
                modifier = Modifier
                    .width(with(LocalDensity.current) { stateHolder.size.width.toDp() })
                    .height(200.dp)
            ) {
                foods.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            addSelectedFoodList(s)
                            removeFoodList(s)
                            stateHolder.onSelectedIndexChange(index)
                            stateHolder.onValueChange(s)
                            stateHolder.onEnabledChange(false)
                        }
                    ) {
                        Text(text = s)
                    }
                }

            }
        }
    }
}

@Composable
fun FoodTag(
    tag: String,
    removeItem: (String) -> Unit,
    addDropDownItem: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(10.dp)
            .noRippleClickable {
                removeItem(tag)
                addDropDownItem(tag)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = tag,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
            
            Spacer(modifier = Modifier.width(5.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_clear),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminAddCategoryScreenPreview() {
    AdminAddCategoryScreen(navController = rememberNavController())
}