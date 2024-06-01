package com.glorfindel.tehanu.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
@Stable
data class TehanuComponentDefaults(
    val horizontalDividerHeight: Dp = 1.dp,
    val verticalDividerWidth: Dp = 1.dp,
    val keyboardThreshold: Int = 300
)
