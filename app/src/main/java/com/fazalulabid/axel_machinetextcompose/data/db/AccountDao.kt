package com.fazalulabid.axel_machinetextcompose.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fazalulabid.axel_machinetextcompose.domain.model.Account

@Dao
interface AccountDao {

    @Insert
    suspend fun insertAccount(account: Account)

    @Query("SELECT COUNT(*) FROM accounts WHERE username = :username")
    suspend fun isUsernameExists(username: String): Int

    @Query("SELECT * FROM accounts WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): Account?

    @Query("SELECT * FROM accounts WHERE username = :username LIMIT 1")
    suspend fun getAccount(username: String): Account?
}