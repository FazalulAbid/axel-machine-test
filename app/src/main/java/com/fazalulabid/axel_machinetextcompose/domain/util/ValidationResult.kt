package com.fazalulabid.axel_machinetextcompose.domain.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage:String? = null
)
