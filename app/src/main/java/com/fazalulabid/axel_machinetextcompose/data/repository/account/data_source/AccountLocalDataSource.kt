package com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source

import com.fazalulabid.axel_machinetextcompose.domain.model.Account

interface AccountLocalDataSource {
    suspend fun insertAccount(account: Account)
    suspend fun isUsernameExists(username: String): Boolean
    suspend fun login(username: String, password: String): Account?
}