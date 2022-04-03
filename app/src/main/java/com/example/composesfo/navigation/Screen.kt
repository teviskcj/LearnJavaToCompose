package com.example.composesfo.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object MenuScreen : Screen("menu_screen")
    object CartScreen : Screen("cart_screen")
    object WalletScreen : Screen("wallet_screen")
    object ProfileScreen : Screen("profile_screen")
    object EditProfileScreen : Screen("edit_profile_screen")
    object LanguageScreen : Screen("language_screen")
    object QuestionsScreen : Screen("questions_screen")
}
