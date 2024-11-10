package com.fazalulabid.axel_machinetextcompose.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PreferenceHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val LOGGED_IN_USER = "logged_in_user"
    }

    fun setLoggedInUser(userId: String) {
        sharedPreferences.edit {
            putString(LOGGED_IN_USER, userId)
        }
    }

    fun getLoggedInUser(): String? =
        sharedPreferences.getString(LOGGED_IN_USER, null)

    fun clearLoggedInUser() {
        sharedPreferences.edit {
            remove(LOGGED_IN_USER)
        }
    }
}