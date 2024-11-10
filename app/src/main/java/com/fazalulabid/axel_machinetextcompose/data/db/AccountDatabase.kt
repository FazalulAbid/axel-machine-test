package com.fazalulabid.axel_machinetextcompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fazalulabid.axel_machinetextcompose.domain.model.Account

@Database(
    entities = [Account::class],
    version = 1,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    companion object {
        const val DATABASE_NAME = "accounts"
    }
}