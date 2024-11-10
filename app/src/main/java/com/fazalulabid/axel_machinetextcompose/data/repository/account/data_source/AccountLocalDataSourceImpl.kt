package com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source

import android.util.Log
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

    override suspend fun getAccount(id: Int): Account? {
        Log.d("Hello", "getAccount: $id")
        return accountDao.getAccount(id)
    }

    override suspend fun updateAccount(account: Account) {
        Log.d("Hello", "updateAccount: ${account.toString()}")
        val updatedRows = accountDao.updateAccount(
            account.id,
            account.username,
            account.fullName,
            account.dob,
            account.profilePictureUri
        )
        Log.d("Hello", "updateAccount: $updatedRows")
    }
}