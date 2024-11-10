package com.fazalulabid.axel_machinetextcompose.domain.usecase.validations

import android.util.Patterns
import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}