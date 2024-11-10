package com.fazalulabid.axel_machinetextcompose.data.repository.account

import com.fazalulabid.axel_machinetextcompose.data.preference.PreferenceHelper
import com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source.AccountLocalDataSource
import com.fazalulabid.axel_machinetextcompose.domain.model.Account
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
    private val preferenceHelper: PreferenceHelper
) : AccountRepository {
    override suspend fun insertAccount(account: Account) {
        accountLocalDataSource.insertAccount(account)
    }

    override suspend fun isUsernameExists(username: String): Boolean {
        return accountLocalDataSource.isUsernameExists(username)
    }

    override suspend fun login(username: String, password: String): Account? {
        val account = accountLocalDataSource.login(username, password) ?: return null
        preferenceHelper.setLoggedInUser(account.username)
        return account
    }

    override suspend fun logout(): Boolean {
        preferenceHelper.clearLoggedInUser()
        return true
    }

    override suspend fun getLoggedInAccount(): Account? {
        val username = preferenceHelper.getLoggedInUser() ?: return null
        return accountLocalDataSource.getAccount(username)
    }
}