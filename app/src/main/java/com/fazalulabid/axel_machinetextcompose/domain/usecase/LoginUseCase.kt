package com.fazalulabid.axel_machinetextcompose.domain.usecase

import com.fazalulabid.axel_machinetextcompose.domain.model.Account
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import com.fazalulabid.axel_machinetextcompose.domain.util.BaseUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseUseCase<LoginUseCase.Params, Account?>() {

    data class Params(val username: String, val password: String)

    override suspend fun execute(params: Params): Account? {
        return accountRepository.login(params.username, params.password)
    }
}