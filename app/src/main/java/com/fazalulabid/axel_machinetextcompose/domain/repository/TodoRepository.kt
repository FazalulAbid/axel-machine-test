package com.fazalulabid.axel_machinetextcompose.domain.repository

import com.fazalulabid.axel_machinetextcompose.domain.model.Todo

interface TodoRepository {
    suspend fun getTodos(): List<Todo>
}