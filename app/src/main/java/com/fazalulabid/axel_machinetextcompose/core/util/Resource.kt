package com.fazalulabid.axel_machinetextcompose.core.util

sealed class Resource<T>(
    val data: T? = null,
    val uiText: UiText? = null,
    val error: Exception? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(uiText: UiText? = null, data: T? = null, error: Exception? = null) :
        Resource<T>(data, uiText, error)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}