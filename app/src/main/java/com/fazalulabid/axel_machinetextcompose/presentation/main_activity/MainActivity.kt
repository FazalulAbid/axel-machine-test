package com.fazalulabid.axel_machinetextcompose.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.fazalulabid.axel_machinetextcompose.presentation.navigation.Navigation
import com.fazalulabid.axel_machinetextcompose.presentation.navigation.Screens
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.AxelMachineTextComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader
    private val viewModel: MainViewModel by viewModels()
    private var startDestination = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        initializeSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        MainUiEvent.NavigateToHomeScreen -> {
                            startDestination.value = Screens.Home.route
                        }

                        MainUiEvent.NavigateToLoginScreen -> {
                            startDestination.value = Screens.Login.route
                        }
                    }
                }
            }
        }

        setContent {
            AxelMachineTextComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                        startDestination.value?.let { startDestination ->
                            Navigation(
                                navController = navController,
                                paddingValues = paddingValues,
                                imageLoader = imageLoader,
                                startDestination = startDestination
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initializeSplashScreen() {
        installSplashScreen().setKeepOnScreenCondition { viewModel.state.value.isSplashLoading }
    }
}