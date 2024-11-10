package com.fazalulabid.axel_machinetextcompose.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fazalulabid.axel_machinetextcompose.R
import com.fazalulabid.axel_machinetextcompose.presentation.components.StandardToolbar
import com.fazalulabid.axel_machinetextcompose.presentation.screens.home.HomeViewModel
import com.fazalulabid.axel_machinetextcompose.presentation.screens.registration.RegistrationEvent
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceLarge
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceMedium
import com.fazalulabid.axel_machinetextcompose.presentation.util.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.formState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginUiEvent.LoginCompleted -> {
                    onLogin()
                }

                is LoginUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message?.asString(context), Toast.LENGTH_SHORT)
                        .show()
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
            TextButton(onClick = {
                viewModel.onEvent(LoginEvent.Submit)
            }) {
                Text(
                    text = stringResource(R.string.login),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }, title = {
            Text(
                text = stringResource(R.string.login),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = SpaceLarge, vertical = SpaceLarge)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Username
            TextField(
                value = state.username,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.UsernameChanged(it))
                },
                isError = state.usernameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Username")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            if (state.usernameError != null) {
                Text(
                    text = state.usernameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

            // Password
            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

            Text("Not registered yet? Click here to register", modifier.clickable {
                navigateToRegister()
            })
        }
    }
}