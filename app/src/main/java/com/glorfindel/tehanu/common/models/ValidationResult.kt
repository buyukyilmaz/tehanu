package com.glorfindel.tehanu.common.models

data class ValidationResult(
    val successful: Boolean = false,
    val message: String? = null
)
