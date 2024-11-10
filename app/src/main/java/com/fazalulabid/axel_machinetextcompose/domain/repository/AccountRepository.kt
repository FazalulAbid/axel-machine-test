package com.fazalulabid.axel_machinetextcompose.domain.repository

import com.fazalulabid.axel_machinetextcompose.domain.model.Account

interface AccountRepository {
    suspend fun insertAccount(account: Account)
    suspend fun isUsernameExists(username: String): Boolean
    suspend fun login(username: String, password: String): Account?
}