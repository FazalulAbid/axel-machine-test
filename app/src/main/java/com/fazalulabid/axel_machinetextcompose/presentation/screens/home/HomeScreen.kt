package com.fazalulabid.axel_machinetextcompose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Text(
            text = "Home",
            style = TextStyle(color = Color.Black)
        )
    }
}