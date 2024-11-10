package com.fazalulabid.axel_machinetextcompose.domain.usecase

import com.fazalulabid.axel_machinetextcompose.domain.model.Account
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import com.fazalulabid.axel_machinetextcompose.domain.util.BaseUseCase
import javax.inject.Inject

class GetLoggedInAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseUseCase<Unit, Account?>() {

    override suspend fun execute(params: Unit): Account? {
        return accountRepository.getLoggedInAccount()
    }
}