package com.fazalulabid.axel_machinetextcompose.domain.usecase.validations

import com.fazalulabid.axel_machinetextcompose.domain.util.ValidationResult
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ValidateDOBUseCase @Inject constructor() {
    fun execute(dob: String): ValidationResult {
        if (dob.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Date of birth can't be blank"
            )
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        try {
            dateFormat.parse(dob)
        } catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid date format. Please use dd/MM/yyyy."
            )
        }

        val today = Calendar.getInstance().time
        val dobDate = dateFormat.parse(dob)
        if (dobDate.after(today)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Date of birth can't be in the future"
            )
        }

        return ValidationResult(successful = true)
    }
}
