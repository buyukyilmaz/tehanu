package com.glorfindel.tehanu.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

object Space {
    @Composable
    @NonRestartableComposable
    fun Vertical(space: Dp) {
        Spacer(modifier = Modifier.height(space))
    }

    @Composable
    @NonRestartableComposable
    fun Horizontal(space: Dp) {
        Spacer(modifier = Modifier.width(space))
    }
}
