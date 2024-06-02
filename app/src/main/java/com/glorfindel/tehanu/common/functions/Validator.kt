package com.glorfindel.tehanu.common.functions

import com.glorfindel.tehanu.common.models.ValidationResult
import com.glorfindel.tehanu.common.models.ValidationSpec
import com.glorfindel.tehanu.extension.greaterThan
import com.glorfindel.tehanu.extension.isEven
import com.glorfindel.tehanu.extension.isOdd
import com.glorfindel.tehanu.extension.lessThan
import com.glorfindel.tehanu.utils.Regexes
import com.glorfindel.tehanu.visualTransformations.GsmTransformation

fun validateInt(
    value: Int?,
    specs: List<ValidationSpec.Int>
): ValidationResult {
    specs.forEach {
        when (it) {
            is ValidationSpec.Int.Null -> {
                if (value == null) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Int.LessThan -> {
                if (value.lessThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Int.GreaterThan -> {
                if (value.greaterThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Int.Odd -> {
                if (value.isOdd()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Int.Even -> {
                if (value.isEven()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }
        }
    }
    return ValidationResult(successful = true)
}

fun validateLong(
    value: Long?,
    specs: List<ValidationSpec.Long>
): ValidationResult {
    specs.forEach {
        when (it) {
            is ValidationSpec.Long.Null -> {
                if (value == null) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Long.LessThan -> {
                if (value.lessThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Long.GreaterThan -> {
                if (value.greaterThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Long.Odd -> {
                if (value.isOdd()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Long.Even -> {
                if (value.isEven()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }
        }
    }
    return ValidationResult(successful = true)
}

fun validateFloat(
    value: Float?,
    specs: List<ValidationSpec.Float>
): ValidationResult {
    specs.forEach {
        when (it) {
            is ValidationSpec.Float.Null -> {
                if (value == null) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Float.LessThan -> {
                if (value.lessThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Float.GreaterThan -> {
                if (value.greaterThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }
        }
    }
    return ValidationResult(successful = true)
}

fun validateDouble(
    value: Double?,
    specs: List<ValidationSpec.Double>
): ValidationResult {
    specs.forEach {
        when (it) {
            is ValidationSpec.Double.Null -> {
                if (value == null) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Double.LessThan -> {
                if (value.lessThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Double.GreaterThan -> {
                if (value.greaterThan(it.value)) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }
        }
    }
    return ValidationResult(successful = true)
}

fun validateBoolean(
    value: Boolean?,
    specs: List<ValidationSpec.Boolean>
): ValidationResult {
    specs.forEach {
        when (it) {
            is ValidationSpec.Boolean.Null -> {
                if (value == null) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Boolean.False -> {
                if (value != null && value.not()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.Boolean.True -> {
                if (value != null && value) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }
        }
    }
    return ValidationResult(successful = true)
}

fun validateString(
    value: String?,
    specs: List<ValidationSpec.String>
): ValidationResult {
    specs.forEach {
        when (it) {
            is ValidationSpec.String.Null -> {
                if (value == null) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.String.Empty -> {
                if (value != null && value.isEmpty()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.String.LengthLessThan -> {
                if (value != null && value.length < it.value) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.String.LengthGreaterThan -> {
                if (value != null && value.length > it.value) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.String.WrongEmail -> {
                if (!value.isNullOrEmpty() && value.matches(Regexes.email).not()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }

            is ValidationSpec.String.WrongGsm -> {
                if (value != null && GsmTransformation.isValid(value).not()) {
                    return ValidationResult(successful = false, message = it.message)
                }
            }
        }
    }
    return ValidationResult(successful = true)
}
