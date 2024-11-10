package com.fazalulabid.axel_machinetextcompose.di

import com.fazalulabid.axel_machinetextcompose.data.db.AccountDatabase
import com.fazalulabid.axel_machinetextcompose.data.repository.account.AccountRepositoryImpl
import com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source.AccountLocalDataSource
import com.fazalulabid.axel_machinetextcompose.data.repository.account.data_source.AccountLocalDataSourceImpl
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountLocalDataSource: AccountLocalDataSource
    ): AccountRepository {
        return AccountRepositoryImpl(accountLocalDataSource)
    }
}