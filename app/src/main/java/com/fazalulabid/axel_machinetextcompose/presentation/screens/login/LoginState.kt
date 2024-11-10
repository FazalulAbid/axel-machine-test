package com.fazalulabid.axel_machinetextcompose.presentation.screens.login

data class LoginState(
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
