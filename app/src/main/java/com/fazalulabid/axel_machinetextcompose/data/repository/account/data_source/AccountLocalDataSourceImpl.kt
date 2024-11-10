package com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source

import com.fazalulabid.axel_machinetextcompose.data.db.AccountDao
import com.fazalulabid.axel_machinetextcompose.domain.model.Account

class AccountLocalDataSourceImpl(
    private val accountDao: AccountDao
) : AccountLocalDataSource {
    override suspend fun insertAccount(account: Account) {
        return accountDao.insertAccount(account)
    }

    override suspend fun isUsernameExists(username: String): Boolean {
        return accountDao.isUsernameExists(username) > 0
    }

    override suspend fun login(username: String, password: String): Account? {
        return accountDao.login(username, password)
    }
}