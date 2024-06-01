package com.glorfindel.tehanu.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

private val TehanuColorsComposition = compositionLocalOf<TehanuColors> { error("need TehanuTheme") }

@Composable
@ReadOnlyComposable
fun tehanuColors() = TehanuColorsComposition.current

private val TehanuComponentDefaultsComposition = compositionLocalOf<TehanuComponentDefaults> { error("need TehanuTheme") }

@Composable
@ReadOnlyComposable
fun tehanuComponentDefaults() = TehanuComponentDefaultsComposition.current

@Composable
fun TehanuTheme(
    tehanuColors: TehanuColors,
    tehanuComponentDefaults: TehanuComponentDefaults,
    content: @Composable () -> Unit
) {
    MaterialTheme {
        CompositionLocalProvider(
            TehanuColorsComposition provides tehanuColors,
            TehanuComponentDefaultsComposition provides tehanuComponentDefaults,
            content = content
        )
    }
}
