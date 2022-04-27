package com.example.composesfo.presentation.admin.adminAddCategory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.orange
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun AdminAddCategoryScreen(
    navController: NavController,
    viewModel: AdminAddCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.stateGetCategory.value

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
                            painter = painterResource(id = R.drawable.ic_left),
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

                Spacer(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f)
                )

                Button(
                    onClick = {
                        val foodCategoryDto = FoodCategoryDto(
                            category = viewModel.category,
                            category_description = viewModel.description,
                            foods = listOf("")
                        )
                        state.categoryList.let {
                            val id = viewModel.createCategoryId(it.size)
                            viewModel.createCategory(id, foodCategoryDto)
                            navController.popBackStack()
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

@Preview(showBackground = true)
@Composable
fun AdminAddCategoryScreenPreview() {
    AdminAddCategoryScreen(navController = rememberNavController())
}