package com.fazalulabid.axel_machinetextcompose.presentation.screens.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fazalulabid.axel_machinetextcompose.presentation.screens.home.HomeViewModel

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

}