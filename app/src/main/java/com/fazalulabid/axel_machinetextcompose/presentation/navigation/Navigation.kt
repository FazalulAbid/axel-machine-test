package com.fazalulabid.axel_machinetextcompose.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import com.fazalulabid.axel_machinetextcompose.presentation.screens.home.HomeScreen
import com.fazalulabid.axel_machinetextcompose.presentation.screens.login.LoginScreen
import com.fazalulabid.axel_machinetextcompose.presentation.screens.registration.RegistrationScreen

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    imageLoader: ImageLoader,
    startDestination: String = Screens.Home.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Home.route) {
            HomeScreen(
                paddingValues = paddingValues,
                navigateToRegisterForEdit = {
                    navController.navigate("${Screens.Registration.route}?editMode=true")
                },
                onLogout = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${Screens.Registration.route}?editMode={editMode}",
            arguments = listOf(navArgument("editMode") { defaultValue = false })
        ) { backStackEntry ->
            val editMode = backStackEntry.arguments?.getBoolean("editMode") ?: false

            RegistrationScreen(
                imageLoader = imageLoader,
                paddingValues = paddingValues,
                editMode = editMode,
                popBackStack = navController::popBackStack,
                navigateToLogin = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Registration.route) {
                            inclusive = true
                        }
                    }
                },
                onRegister = {
                    navController.popBackStack(
                        route = Screens.Registration.route,
                        inclusive = true
                    )
                    navController.navigate(Screens.Login.route)
                },
            )
        }

        composable(Screens.Login.route) {
            LoginScreen(
                paddingValues = paddingValues,
                onLogin = {
                    navController.popBackStack(
                        route = Screens.Login.route,
                        inclusive = true
                    )
                    navController.navigate(Screens.Home.route)
                },
                navigateToRegister = {
                    navController.navigate(Screens.Registration.route) {
                        popUpTo(Screens.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}