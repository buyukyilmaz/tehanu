package com.glorfindel.tehanu.common.functions

import com.glorfindel.tehanu.common.callbacks.VoidCallback

fun <T> tryOrNull(block: () -> T?): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

fun <T> tryOrDefault(
    default: T?,
    block: () -> T?
): T? {
    return try {
        block()
    } catch (e: Exception) {
        default
    }
}

fun justTry(block: VoidCallback) {
    try {
        block()
    } catch (e: Exception) {
    }
}
