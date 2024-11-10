package com.fazalulabid.axel_machinetextcompose.data.repository.account

import com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source.AccountLocalDataSource
import com.fazalulabid.axel_machinetextcompose.domain.model.Account
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
) : AccountRepository {
    override suspend fun insertAccount(account: Account) {
        accountLocalDataSource.insertAccount(account)
    }

    override suspend fun isUsernameExists(username: String): Boolean {
        return accountLocalDataSource.isUsernameExists(username)
    }

    override suspend fun login(username: String, password: String): Account? {
        return accountLocalDataSource.login(username, password)
    }
}