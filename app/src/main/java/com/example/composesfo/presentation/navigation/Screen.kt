package com.example.composesfo.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object MenuScreen : Screen("menu_screen")
    object FoodDetailsScreen : Screen("food_details_screen")
    object CartScreen : Screen("cart_screen")
    object PaymentScreen : Screen("payment_screen")
    object WalletScreen : Screen("wallet_screen")
    object ReloadFormScreen : Screen("reload_form_screen")
    object ProfileScreen : Screen("profile_screen")
    object EditProfileScreen : Screen("edit_profile_screen")
    object OrderScreen : Screen("order_screen")
    object OrderDetailsScreen : Screen("order_details_screen")
    object LanguageScreen : Screen("language_screen")
    object TranslatorScreen : Screen("translator_screen")
}
