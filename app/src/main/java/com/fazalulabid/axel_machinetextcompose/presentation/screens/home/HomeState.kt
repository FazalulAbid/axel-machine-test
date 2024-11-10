package com.fazalulabid.axel_machinetextcompose.presentation.screens.home

import com.fazalulabid.axel_machinetextcompose.domain.model.Todo

data class HomeState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList()
)
