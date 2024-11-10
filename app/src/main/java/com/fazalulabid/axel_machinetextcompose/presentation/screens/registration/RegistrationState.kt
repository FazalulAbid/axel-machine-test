package com.fazalulabid.axel_machinetextcompose.presentation.screens.registration

import android.net.Uri

data class RegistrationState(
    val profilePictureUri: Uri? = null,

    val username: String = "",
    val usernameError: String? = null,

    val fullName: String = "",
    val fullNameError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,

    val dob: String = "",
    val dobError: String? = null
)