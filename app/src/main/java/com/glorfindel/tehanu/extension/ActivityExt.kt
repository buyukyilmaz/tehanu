package com.glorfindel.tehanu.extension

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.glorfindel.tehanu.common.functions.justTry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> ComponentActivity.collectLifecycleAware(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    collect: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collect(collect)
        }
    }
}

fun <T> ComponentActivity.collectLatestLifecycleAware(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    collect: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collectLatest(collect)
        }
    }
}

fun Activity.hideKeyboard() {
    justTry {
        val view = currentFocus ?: this.window.decorView
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
