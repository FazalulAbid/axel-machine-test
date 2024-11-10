package com.fazalulabid.axel_machinetextcompose.data.repository.todo

import com.fazalulabid.axel_machinetextcompose.data.repository.todo.data_source.TodoRemoteDataSource
import com.fazalulabid.axel_machinetextcompose.domain.model.Todo
import com.fazalulabid.axel_machinetextcompose.domain.repository.TodoRepository
import java.io.IOException
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoRemoteDataSource: TodoRemoteDataSource
) : TodoRepository {

    override suspend fun getTodos(): List<Todo> {
        val response = todoRemoteDataSource.getTodos()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw IOException("Failed to fetch todos with code: ${response.code()}")
        }
    }
}