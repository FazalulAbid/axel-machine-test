package com.fazalulabid.axel_machinetextcompose.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazalulabid.axel_machinetextcompose.core.util.Resource
import com.fazalulabid.axel_machinetextcompose.domain.usecase.GetLoggedInAccountUseCase
import com.fazalulabid.axel_machinetextcompose.presentation.util.Constants.SPLASHSCREEN_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoggedInAccountUseCase: GetLoggedInAccountUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private val _uiEvent = MutableSharedFlow<MainUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getLoggedInAccount()
    }

    private fun getLoggedInAccount() {
        viewModelScope.launch {
            delay(SPLASHSCREEN_DELAY)
            getLoggedInAccountUseCase(Unit).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiEvent.emit(MainUiEvent.NavigateToLoginScreen)
                        _state.value = state.value.copy(
                            isSplashLoading = false
                        )
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        if (result.data == null) {
                            _uiEvent.emit(MainUiEvent.NavigateToLoginScreen)
                        } else {
                            _state.value = state.value.copy(
                                account = result.data
                            )
                            _uiEvent.emit(MainUiEvent.NavigateToHomeScreen)
                        }
                        _state.value = state.value.copy(
                            isSplashLoading = false
                        )
                    }
                }
            }
        }
    }
}

sealed class MainUiEvent {
    data object NavigateToLoginScreen : MainUiEvent()
    data object NavigateToHomeScreen : MainUiEvent()
}