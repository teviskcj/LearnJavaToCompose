package com.example.composesfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.view.*

@Composable
fun Navigation() {
    //lateinit var navController: NavHostController
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.RegisterScreen.route
    ) {
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }
        composable(Screen.LanguageScreen.route) {
            LanguageScreen(navController = navController)
        }
        composable(Screen.QuestionsScreen.route) {
            QuestionsScreen(navController = navController)
        }
    }
}