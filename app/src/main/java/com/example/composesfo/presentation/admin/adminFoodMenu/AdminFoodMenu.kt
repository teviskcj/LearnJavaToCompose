package com.example.composesfo.presentation.admin.adminFoodMenu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.presentation.component.MultiFabItem
import com.example.composesfo.presentation.component.MultiFabState
import com.example.composesfo.presentation.component.MultiFloatingActionButton
import com.example.composesfo.presentation.component.noRippleClickable
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.black
import com.example.composesfo.presentation.ui.theme.lightgraybg

@Composable
fun AdminFoodMenuScreen(
    navController: NavController,
    viewModel: AdminFoodMenuViewModel = hiltViewModel()
) {
    val stateCategory = viewModel.stateGetCategory.value
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
                            painter = painterResource(id = R.drawable.ic_left),
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
                }
            )
        },
        backgroundColor = lightgraybg,
        floatingActionButton = {
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
                    navController.navigate(route = Screen.AdminAddCategoryScreen.route)
                }

                if (it.identifier == "menu") {
                    navController.navigate(route = Screen.AdminAddMenuScreen.route)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
            Spacer(modifier = Modifier.padding(10.dp))
            TypeList(
                list = viewModel.getCategoryList(stateCategory.categoryList),
                getColor = viewModel::getColor,
                onTypeChange = viewModel::onTypeChange
            )
        }
        val alpha = if (toState == MultiFabState.EXPANDED) 0.4f else 0f
        Box(
            modifier = Modifier
                .alpha(animateFloatAsState(alpha).value)
                .background(black)
                .fillMaxSize()
        )
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

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    AdminFoodMenuScreen(navController = rememberNavController())
}