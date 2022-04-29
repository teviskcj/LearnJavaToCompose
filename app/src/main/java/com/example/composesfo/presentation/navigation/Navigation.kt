package com.example.composesfo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.presentation.HomeScreen
import com.example.composesfo.presentation.admin.adminAddCategory.AdminAddCategoryScreen
import com.example.composesfo.presentation.admin.adminAddFood.AdminAddFoodScreen
import com.example.composesfo.presentation.admin.adminFoodMenu.AdminFoodMenuScreen
import com.example.composesfo.presentation.admin.adminHome.AdminHomeScreen
import com.example.composesfo.presentation.admin.foodCategoryMenu.FoodCategoryMenuScreen
import com.example.composesfo.presentation.cart.CartScreen
import com.example.composesfo.presentation.foodDetails.FoodDetailsScreen
import com.example.composesfo.presentation.foodMenu.MenuScreen
import com.example.composesfo.presentation.language.LanguageScreen
import com.example.composesfo.presentation.login.LoginScreen
import com.example.composesfo.presentation.order.OrderScreen
import com.example.composesfo.presentation.orderDetail.OrderDetailsScreen
import com.example.composesfo.presentation.payment.PaymentScreen
import com.example.composesfo.presentation.profile.EditProfileScreen
import com.example.composesfo.presentation.profile.ProfileScreen
import com.example.composesfo.presentation.register.RegisterScreen
import com.example.composesfo.presentation.translator.TranslatorScreen
import com.example.composesfo.presentation.wallet.ReloadFormScreen
import com.example.composesfo.presentation.wallet.WalletScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Composable
fun Navigation() {
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

        /**
         * User Navigation
         * */
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.MenuScreen.route) {
            MenuScreen(navController = navController)
        }
        composable(Screen.CartScreen.route) {
            CartScreen(navController = navController)
        }
        composable(Screen.PaymentScreen.route) {
            PaymentScreen(navController = navController)
        }
        composable(Screen.FoodDetailsScreen.route + "/{foodId}") {
            FoodDetailsScreen(navController = navController)
        }
        composable(Screen.WalletScreen.route) {
            WalletScreen(navController = navController)
        }
        composable(Screen.ReloadFormScreen.route) {
            ReloadFormScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }
        composable(Screen.OrderScreen.route) {
            OrderScreen(navController = navController)
        }
        composable(Screen.OrderDetailsScreen.route + "/{orderId}") {
            OrderDetailsScreen(navController = navController)
        }
        composable(Screen.LanguageScreen.route) {
            LanguageScreen(navController = navController)
        }
        composable(Screen.TranslatorScreen.route) {
            TranslatorScreen(navController = navController)
        }

        /**
         * Admin Navigation
         * */
        composable(Screen.AdminHomeScreen.route) {
            AdminHomeScreen(navController = navController)
        }
        
        composable(Screen.AdminFoodMenuScreen.route) {
            AdminFoodMenuScreen(navController = navController)
        }

        composable(Screen.FoodCategoryMenuScreen.route) {
            FoodCategoryMenuScreen(navController = navController)
        }

        composable(Screen.AdminAddCategoryScreen.route) {
            AdminAddCategoryScreen(navController = navController)
        }

        composable(Screen.AdminAddFoodScreen.route) {
            AdminAddFoodScreen(navController = navController)
        }
    }
}