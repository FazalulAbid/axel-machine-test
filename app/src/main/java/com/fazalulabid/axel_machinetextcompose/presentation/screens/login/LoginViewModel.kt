package com.fazalulabid.axel_machinetextcompose.presentation.screens.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazalulabid.axel_machinetextcompose.core.util.Resource
import com.fazalulabid.axel_machinetextcompose.core.util.UiText
import com.fazalulabid.axel_machinetextcompose.domain.usecase.LoginUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidatePasswordUseCase
import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _formState = mutableStateOf(LoginState())
    val formState: State<LoginState> = _formState

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PasswordChanged -> {
                _formState.value = _formState.value.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is LoginEvent.UsernameChanged -> {
                _formState.value = _formState.value.copy(
                    username = event.username,
                    usernameError = null
                )
            }

            LoginEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val usernameResult = if (_formState.value.username.isEmpty()) {
            ValidationResult(
                successful = false,
                errorMessage = "The username can't be empty"
            )
        } else {
            ValidationResult(
                successful = true
            )
        }
        val passwordResult = validatePasswordUseCase.execute(_formState.value.password)

        val hasError = listOf(
            usernameResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            _formState.value = _formState.value.copy(
                usernameError = usernameResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            loginUseCase(
                LoginUseCase.Params(
                    username = _formState.value.username,
                    password = _formState.value.password
                )
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        if (result.data == null) {
                            _eventFlow.emit(LoginUiEvent.ShowToast(UiText.DynamicString("Login failed")))
                            return@collect
                        } else {
                            _eventFlow.emit(LoginUiEvent.LoginCompleted)
                        }
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(LoginUiEvent.ShowToast(result.uiText))
                    }
                }
            }
        }
    }
}

sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data object Submit : LoginEvent()
}

sealed class LoginUiEvent {
    data class ShowToast(val message: UiText?) : LoginUiEvent()
    data object LoginCompleted : LoginUiEvent()
}