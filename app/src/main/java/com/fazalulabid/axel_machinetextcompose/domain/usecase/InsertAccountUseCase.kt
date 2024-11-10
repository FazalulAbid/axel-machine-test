package com.fazalulabid.axel_machinetextcompose.domain.usecase

import com.fazalulabid.axel_machinetextcompose.domain.model.Account
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import com.fazalulabid.axel_machinetextcompose.domain.util.BaseUseCase
import javax.inject.Inject

class InsertAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseUseCase<InsertAccountUseCase.Params, Unit>() {

    data class Params(val account: Account)

    override suspend fun execute(params: Params) {
        accountRepository.insertAccount(params.account)
    }
}