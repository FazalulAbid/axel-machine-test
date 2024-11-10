package com.fazalulabid.axel_machinetextcompose.domain.usecase.validations

import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least of 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least a letter and digit"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}