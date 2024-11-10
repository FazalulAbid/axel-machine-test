package com.fazalulabid.axel_machinetextcompose.data.repository.todo.data_source

import com.fazalulabid.axel_machinetextcompose.data.remote.TodoApiService
import com.fazalulabid.axel_machinetextcompose.domain.model.Todo
import retrofit2.Response
import javax.inject.Inject

class TodoRemoteDataSourceImpl @Inject constructor(
    private val apiService: TodoApiService
) : TodoRemoteDataSource {

    override suspend fun getTodos(): Response<List<Todo>> {
        return apiService.getTodos()
    }
}