package com.fazalulabid.axel_machinetextcompose.data.repository.todo.data_source

import com.fazalulabid.axel_machinetextcompose.domain.model.Todo
import retrofit2.Response

interface TodoRemoteDataSource {
    suspend fun getTodos(): Response<List<Todo>>
}