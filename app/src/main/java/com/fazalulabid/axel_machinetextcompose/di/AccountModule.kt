package com.fazalulabid.axel_machinetextcompose.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fazalulabid.axel_machinetextcompose.data.db.AccountDatabase
import com.fazalulabid.axel_machinetextcompose.data.preference.PreferenceHelper
import com.fazalulabid.axel_machinetextcompose.data.repository.account.AccountRepositoryImpl
import com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source.AccountLocalDataSource
import com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source.AccountLocalDataSourceImpl
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    @Singleton
    fun provideAccountLocalDataSource(
        db: AccountDatabase
    ): AccountLocalDataSource = AccountLocalDataSourceImpl(db.accountDao())

    @Suppress("DEPRECATION")
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun providePreferenceHelper(sharedPreferences: SharedPreferences): PreferenceHelper =
        PreferenceHelper(sharedPreferences)

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountLocalDataSource: AccountLocalDataSource,
        preferenceHelper: PreferenceHelper
    ): AccountRepository {
        return AccountRepositoryImpl(accountLocalDataSource, preferenceHelper)
    }
}