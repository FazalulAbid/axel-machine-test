package com.fazalulabid.axel_machinetextcompose.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazalulabid.axel_machinetextcompose.core.util.Resource
import com.fazalulabid.axel_machinetextcompose.core.util.UiText
import com.fazalulabid.axel_machinetextcompose.domain.usecase.GetTodosUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getTodos()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase(Unit).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _eventFlow.emit(HomeUiEvent.ShowToast(result.uiText))
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        _eventFlow.emit(HomeUiEvent.AccountLoggedOut)
                    }
                }
            }
        }
    }

    private fun getTodos() {
        viewModelScope.launch {
            getTodosUseCase(Unit).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            todos = result.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(
                            HomeUiEvent.ShowToast(result.uiText)
                        )
                    }
                }
            }
        }
    }
}

sealed class HomeUiEvent {
    data class ShowToast(val message: UiText?) : HomeUiEvent()
    data object AccountLoggedOut : HomeUiEvent()
}

sealed class HomeEvent {
    data object Logout : HomeEvent()
}