package com.glorfindel.tehanu.extension

import androidx.compose.runtime.Composable

@Composable
fun <T> List<T>?.NotNullAndNotEmpty(block: @Composable (List<T>) -> Unit) {
    this?.let {
        if (it.isNotEmpty()) {
            block(it)
        }
    }
}

fun <T> Collection<T>?.isNotNullAndNotEmpty(): Boolean = isNullOrEmpty().not()

fun <T> Collection<T>?.isNull(): Boolean = this == null

fun <T> Collection<T>?.isNotNull(): Boolean = !this.isNull()
