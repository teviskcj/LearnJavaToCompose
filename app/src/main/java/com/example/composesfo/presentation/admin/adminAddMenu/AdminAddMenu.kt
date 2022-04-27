package com.example.composesfo.presentation.admin.adminAddMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.presentation.admin.adminAddCategory.AdminAddCategoryViewModel

@Composable
fun AdminAddMenuScreen(
    navController: NavController,
    viewModel: AdminAddCategoryViewModel = hiltViewModel()
) {

}

@Preview(showBackground = true)
@Composable
fun AdminAddMenuScreenPreview() {
    AdminAddMenuScreen(navController = rememberNavController())
}