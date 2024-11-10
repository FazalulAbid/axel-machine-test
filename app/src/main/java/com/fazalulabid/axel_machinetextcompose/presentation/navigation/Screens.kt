package com.fazalulabid.axel_machinetextcompose.presentation.navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home_screen")
    data object Registration : Screens("registration_screen")
    data object Login : Screens("login_screen")
}
