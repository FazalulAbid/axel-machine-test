package com.fazalulabid.axel_machinetextcompose.domain.usecase.validations

import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import javax.inject.Inject

class ValidateFullNameUseCase @Inject constructor() {
    fun execute(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Full name can't be blank"
            )
        }

        if (fullName.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = "Full name must be at least 3 characters long"
            )
        }

        if (fullName.length > 30) {
            return ValidationResult(
                successful = false,
                errorMessage = "Full name must not exceed 30 characters"
            )
        }

        return ValidationResult(successful = true)
    }
}
