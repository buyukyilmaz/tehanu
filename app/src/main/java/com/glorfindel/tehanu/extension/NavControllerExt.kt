package com.glorfindel.tehanu.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController

@Composable
fun <T> NavController.GetResult(
    key: String,
    onResult: (T) -> Unit
) {
    val result = currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observeAsState()

    result?.value?.let {
        currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        onResult(it)
    }
}

fun <T> NavController.setResult(
    key: String,
    data: T
) {
    previousBackStackEntry?.savedStateHandle?.set(key, data)
}
