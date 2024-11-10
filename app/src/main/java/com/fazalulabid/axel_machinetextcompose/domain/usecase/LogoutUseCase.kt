package com.fazalulabid.axel_machinetextcompose.domain.usecase

import com.fazalulabid.axel_machinetextcompose.R
import com.fazalulabid.axel_machinetextcompose.core.util.Resource
import com.fazalulabid.axel_machinetextcompose.core.util.UiText
import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import com.fazalulabid.axel_machinetextcompose.domain.util.BaseUseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseUseCase<Unit, Resource<Boolean>>() {

    override suspend fun execute(params: Unit): Resource<Boolean> {
        return try {
            val success = accountRepository.logout()
            if (success) {
                Resource.Success(true)
            } else {
                Resource.Error(
                    uiText = UiText.StringResource(R.string.error_logout_failed)
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_logout_failed),
                error = e
            )
        }
    }
}
