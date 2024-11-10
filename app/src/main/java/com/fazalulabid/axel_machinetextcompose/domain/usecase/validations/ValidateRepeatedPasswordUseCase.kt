package com.fazalulabid.axel_machinetextcompose.domain.usecase.validations

import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import javax.inject.Inject

class ValidateRepeatedPasswordUseCase @Inject constructor() {
    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}