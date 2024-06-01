package com.glorfindel.tehanu.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier

@Composable
@NonRestartableComposable
fun ScreenWithState(
    modifier: Modifier,
    state: ScreenState,
    loading: (@Composable (Modifier) -> Unit)? = null,
    error: (@Composable (Modifier) -> Unit)? = null,
    content: (@Composable (Modifier) -> Unit)? = null
) {
    when (state) {
        ScreenState.Content -> {
            content?.let { it(modifier) }
        }

        ScreenState.Empty -> {}

        ScreenState.Loading -> {
            loading?.let { it(modifier) }
        }

        ScreenState.Error -> {
            error?.let { it(modifier) }
        }
    }
}

sealed class ScreenState {
    data object Loading : ScreenState()

    data object Error : ScreenState()

    data object Empty : ScreenState()

    data object Content : ScreenState()
}
