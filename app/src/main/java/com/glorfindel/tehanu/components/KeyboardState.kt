package com.glorfindel.tehanu.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import com.glorfindel.tehanu.theme.tehanuComponentDefaults

@Composable
fun keyboardState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > tehanuComponentDefaults().keyboardThreshold
    return rememberUpdatedState(isImeVisible)
}
