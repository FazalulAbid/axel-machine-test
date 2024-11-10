package com.fazalulabid.axel_machinetextcompose.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazalulabid.axel_machinetextcompose.R
import com.fazalulabid.axel_machinetextcompose.presentation.components.StandardToolbar
import com.fazalulabid.axel_machinetextcompose.presentation.screens.home.components.TodoItem
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceLarge
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceMedium

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        StandardToolbar(onNavigateUp = {}, showBackArrow = false, navActions = {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.save_changes),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }, title = {
            Text(
                text = stringResource(R.string.todos),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        })

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