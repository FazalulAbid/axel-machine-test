package com.fazalulabid.axel_machinetextcompose.data.remote

import com.fazalulabid.axel_machinetextcompose.domain.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>
}