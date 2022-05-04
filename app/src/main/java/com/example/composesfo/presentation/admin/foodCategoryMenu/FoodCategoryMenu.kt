package com.example.composesfo.presentation.admin.foodCategoryMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.composesfo.R
import com.example.composesfo.data.remote.dto.FoodCategoryDto
import com.example.composesfo.presentation.component.MultipleSelectListItem
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.lightgraybg

@Composable
fun FoodCategoryMenuScreen(
    navController: NavController,
    viewModel: FoodCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.stateGetCategories.value

    var items by remember {
        mutableStateOf(
            (1..20).map {
                MultipleSelectListItem(
                    title = "Item $it",
                    isSelected = false
                )
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Food Category") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp,
                actions = {
                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = ""
                        )
                    }

                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = ""
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(route = Screen.AdminAddCategoryScreen.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        backgroundColor = lightgraybg
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.categoryList) { category ->
                CategoryCard(
                    category = category,
                    isSelected = viewModel.isSelected,
                    isSelectedChange = viewModel::isSelectedChange
                )
            }
            /*items(items.size) { i ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            items = items.mapIndexed { j, item ->
                                if (i == j) {
                                    item.copy(isSelected = !item.isSelected)
                                } else item
                            }
                        }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = items[i].title)
                    if (items[i].isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.Green,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }*/
        }
    }
}

@Composable
fun CategoryList(

) {

}

@Composable
fun CategoryCard(
    category: FoodCategoryDto,
    isSelected: Boolean,
    isSelectedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = { isSelectedChange(!isSelected) })
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = category.category_name,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = category.category_description,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }

            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Selected",
                    tint = Color.Green,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier.padding(
                top = 10.dp
            )
        )
    }
    /*Row(
        modifier = Modifier
            .clickable(onClick = { isSelectedChange(!isSelected) })
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

            }
            Text(
                text = category.category,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = category.category_description,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
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

        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Selected",
                tint = Color.Green,
                modifier = Modifier.size(50.dp).weight(1f)
            )
        }
    }*/
}

@Preview
@Composable
fun FoodCategoryMenuScreenPreview() {
    FoodCategoryMenuScreen(navController = rememberNavController())
}