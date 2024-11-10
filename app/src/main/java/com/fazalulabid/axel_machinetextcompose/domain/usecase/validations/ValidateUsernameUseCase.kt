package com.fazalulabid.axel_machinetextcompose.domain.usecase.validations

import com.fazalulabid.axel_machinetextcompose.domain.repository.AccountRepository
import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(username: String, isUpdate: Boolean = false): ValidationResult {
        if (username.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Username can't be blank"
            )
        }

        if (!isUpdate) {
            val isExists = accountRepository.isUsernameExists(username)
            if (isExists) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Username already exists"
                )
            }
        }

        return ValidationResult(successful = true)
    }
}