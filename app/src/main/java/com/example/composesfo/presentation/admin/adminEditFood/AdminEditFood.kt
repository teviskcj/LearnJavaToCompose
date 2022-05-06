package com.example.composesfo.presentation.admin.adminEditFood

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.composesfo.R
import com.example.composesfo.common.Constants
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.presentation.component.GallerySelect
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.orange
import com.example.composesfo.presentation.ui.theme.red
import com.example.composesfo.presentation.ui.theme.white
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Composable
fun AdminEditFoodScreen(
    navController: NavController,
    viewModel: AdminEditFoodViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val food = state.food
    var imageUri by remember { mutableStateOf(Constants.EMPTY_IMAGE_URI) }
    var showGallerySelect by remember { mutableStateOf(false) }

    food?.run {
        viewModel.onNameChange(food_name)
        viewModel.onDescriptionChange(food_description)
        viewModel.onPriceChange(food_price)
        imageUri = Uri.parse(food_image_url)
    }

    if (viewModel.showGallery) {
        GallerySelect(
            modifier = Modifier,
            onImageUri = { uri ->
                showGallerySelect = false
                imageUri = uri
            }
        )
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
                    Text(text = "Add Food")
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
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = stringResource(R.string.food_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 30.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            viewModel.onShowGalleryChange(true)
                        }
                )

                TextFieldWithNoIcon(
                    text = viewModel.name,
                    onTextChange = viewModel::onNameChange,
                    label = "Food Name",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(10.dp))

                TextFieldWithNoIcon(
                    text = viewModel.description,
                    onTextChange = viewModel::onDescriptionChange,
                    label = "Food Description",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(10.dp))

                TextFieldWithNoIcon(
                    text = viewModel.price,
                    onTextChange = viewModel::onPriceChange,
                    label = "Food Price",
                    modifier = Modifier
                )

                Spacer(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f)
                )

                Button(
                    onClick = {
                        food?.run {
                            val foodDto = FoodDto(
                                id = id,
                                food_description = viewModel.description,
                                food_image_url = imageUri.toString(),
                                food_name = viewModel.name,
                                food_price = viewModel.price
                            )
                            viewModel.editFood(id, foodDto)

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
                        text = "Edit Food",
                        color = white,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp,
                            bottom = 34.dp
                        ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Delete Food",
                        color = white,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Preview(showBackground = true)
@Composable
fun AdminEditFoodScreenPreview() {
    AdminEditFoodScreen(navController = rememberNavController())
}