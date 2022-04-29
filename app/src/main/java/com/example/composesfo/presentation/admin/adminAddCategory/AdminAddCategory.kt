package com.example.composesfo.presentation.admin.adminAddCategory

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.presentation.component.ExposedDropMenuStateHolder
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.component.rememberExposedMenuStateHolder
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.orange
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun AdminAddCategoryScreen(
    navController: NavController,
    viewModel: AdminAddCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.stateGetCategory.value
    val stateHolder = rememberExposedMenuStateHolder()
    val foodList = listOf("food 1", "food 2", "food 3")

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFieldWithNoIcon(
                    text = viewModel.category,
                    onTextChange = viewModel::onCategoryChange,
                    label = "Category",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(20.dp))

                TextFieldWithNoIcon(
                    text = viewModel.description,
                    onTextChange = viewModel::onDescriptionChange,
                    label = "Description",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(20.dp))

                FoodsDropdownList(
                    stateHolder = stateHolder,
                    foods = foodList
                )

                Spacer(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f)
                )

                Button(
                    onClick = {
                        state.categoryList.let {
                            //val id = viewModel.createCategoryId(it.size)
                            val id = viewModel.getCategoryId(it)

                            val foodCategoryDto = FoodCategoryDto(
                                id = id,
                                category_name = viewModel.category,
                                category_description = viewModel.description,
                                foods = listOf("")
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
    foods: List<String>
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Box (
            modifier = Modifier.fillMaxWidth()
        )  {
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
                modifier = Modifier.width(with(LocalDensity.current){stateHolder.size.width.toDp()})
            ) {
                foods.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
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

@Preview(showBackground = true)
@Composable
fun AdminAddCategoryScreenPreview() {
    AdminAddCategoryScreen(navController = rememberNavController())
}