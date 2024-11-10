package com.fazalulabid.axel_machinetextcompose.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazalulabid.axel_machinetextcompose.R
import com.fazalulabid.axel_machinetextcompose.presentation.components.StandardToolbar
import com.fazalulabid.axel_machinetextcompose.presentation.screens.home.components.TodoItem
import com.fazalulabid.axel_machinetextcompose.presentation.util.asString

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToRegisterForEdit: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is HomeUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message?.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }

                HomeUiEvent.AccountLoggedOut -> {
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    onLogout()
                }
            }
        }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        StandardToolbar(onNavigateUp = {}, showBackArrow = false, navActions = {
            // Edit Profile
            IconButton(onClick = {
                navigateToRegisterForEdit()
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.save_changes),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            // Logout
            TextButton(onClick = {
                viewModel.onEvent(HomeEvent.Logout)
            }) {
                Text(
                    text = stringResource(R.string.logout),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }, title = {
            Text(
                text = stringResource(R.string.todos),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        })

        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(state.todos) {
                        TodoItem(todo = it)
                    }
                }
            }
        }

    }
}