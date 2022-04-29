package com.example.composesfo.presentation.admin.adminAddFood

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
import com.example.composesfo.common.Constants.EMPTY_IMAGE_URI
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.presentation.component.GallerySelect
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.orange
import com.example.composesfo.presentation.ui.theme.white
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Composable
fun AdminAddFoodScreen(
    navController: NavController,
    viewModel: AdminAddFoodViewModel = hiltViewModel()
) {
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
            var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
            var showGallerySelect by remember { mutableStateOf(false) }

            if (viewModel.showGallery) {
                GallerySelect(
                    modifier = Modifier,
                    onImageUri = { uri ->
                        showGallerySelect = false
                        imageUri = uri
                    }
                )
            }

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
                        val id = viewModel.getFoodId()
                        val foodDto = FoodDto(
                            id = id,
                            food_description = viewModel.description,
                            food_image_url = imageUri.toString(),
                            food_name = viewModel.name,
                            food_price = viewModel.price
                        )

                        viewModel.createCategory(id, foodDto)

                        navController.navigate(route = Screen.AdminFoodMenuScreen.route) {
                            popUpTo(Screen.AdminHomeScreen.route) {
                                inclusive = true
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
                        text = "Add Food",
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
fun AdminAddFoodScreenPreview() {
    AdminAddFoodScreen(navController = rememberNavController())
}