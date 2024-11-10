package com.fazalulabid.axel_machinetextcompose.di

import com.fazalulabid.axel_machinetextcompose.data.remote.TodoApiService
import com.fazalulabid.axel_machinetextcompose.data.repository.todo.TodoRepositoryImpl
import com.fazalulabid.axel_machinetextcompose.data.repository.todo.data_source.TodoRemoteDataSource
import com.fazalulabid.axel_machinetextcompose.data.repository.todo.data_source.TodoRemoteDataSourceImpl
import com.fazalulabid.axel_machinetextcompose.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideTrendingGitsApiService(
        client: OkHttpClient
    ): TodoApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(TodoApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoRemoteDataSource(
        apiService: TodoApiService
    ): TodoRemoteDataSource = TodoRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideTodoRepository(
        remoteDataSource: TodoRemoteDataSource
    ): TodoRepository = TodoRepositoryImpl(remoteDataSource)
}