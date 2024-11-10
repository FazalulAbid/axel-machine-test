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

    fun setLoggedInUser(userId: Int) {
        sharedPreferences.edit {
            putInt(LOGGED_IN_USER, userId)
        }
    }

    fun getLoggedInUserId(): Int =
        sharedPreferences.getInt(LOGGED_IN_USER, 0)

    fun clearLoggedInUser() {
        sharedPreferences.edit {
            remove(LOGGED_IN_USER)
        }
    }
}