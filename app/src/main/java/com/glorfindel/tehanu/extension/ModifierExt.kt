package com.glorfindel.tehanu.extension

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import com.glorfindel.tehanu.common.callbacks.VoidCallback
import com.glorfindel.tehanu.core.TehanuDefaults
import com.glorfindel.tehanu.utils.SingleClick

inline fun Modifier.singleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    defaultDelay: Long = TehanuDefaults.Common.singleClickDelay,
    crossinline onClick: () -> Unit
): Modifier =
    then(
        clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role
        ) {
            SingleClick.clickOnce({ onClick() }, defaultDelay)
        }
    )

fun Modifier.applyIf(
    predicate: () -> Boolean,
    apply: Modifier.() -> Modifier
): Modifier {
    return if (predicate()) {
        apply(this)
    } else {
        this
    }
}

fun Modifier.applyIfNot(
    predicate: () -> Boolean,
    apply: Modifier.() -> Modifier
): Modifier {
    return if (predicate().not()) {
        apply(this)
    } else {
        this
    }
}

inline fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    defaultDelay: Long = TehanuDefaults.Common.singleClickDelay,
    crossinline onClick: () -> Unit
): Modifier =
    then(
        composed {
            clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = enabled,
                onClickLabel = onClickLabel,
                role = role
            ) {
                SingleClick.clickOnce({ onClick() }, defaultDelay)
            }
        }
    )

fun Modifier.bounceClick(
    onClick: VoidCallback,
    minScale: Float = 0.7f
) = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) minScale else 1f,
        label = ""
    ) {
        if (isPressed) {
            isPressed = false
        }
    }

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            isPressed = true
            SingleClick.clickOnce(onClick)
        }
}
