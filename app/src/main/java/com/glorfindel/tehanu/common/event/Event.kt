package com.glorfindel.tehanu.common.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable

@Composable
@NonRestartableComposable
fun Event(
    event: MutableState<StateEvent>,
    consumeBefore: Boolean = false,
    action: suspend () -> Unit
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEvent.Triggered) {
            if (consumeBefore) {
                event.value = consumed
            }
            action()
            if (consumeBefore.not()) {
                event.value = consumed
            }
        }
    }
}

@Composable
@NonRestartableComposable
fun <T> Event(
    event: MutableState<StateEventWithContent<T>>,
    consumeBefore: Boolean = false,
    action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered<T>) {
            val value = event.value as StateEventWithContentTriggered<T>
            if (consumeBefore) {
                event.value = consumed()
            }
            action(value.content)
            if (consumeBefore.not()) {
                event.value = consumed()
            }
        }
    }
}

@Composable
@NonRestartableComposable
fun <T1, T2> Event(
    event: MutableState<StateEventWithContent2<T1, T2>>,
    consumeBefore: Boolean = false,
    action: suspend (T1, T2) -> Unit
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered2<T1, T2>) {
            val value = event.value as StateEventWithContentTriggered2<T1, T2>
            if (consumeBefore) {
                event.value = consumed2()
            }
            action(value.content1, value.content2)
            if (consumeBefore.not()) {
                event.value = consumed2()
            }
        }
    }
}

@Composable
@NonRestartableComposable
fun <T1, T2, T3> Event(
    event: MutableState<StateEventWithContent3<T1, T2, T3>>,
    consumeBefore: Boolean = false,
    action: suspend (T1, T2, T3) -> Unit
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered3<T1, T2, T3>) {
            val value = event.value as StateEventWithContentTriggered3<T1, T2, T3>
            if (consumeBefore) {
                event.value = consumed3()
            }
            action(value.content1, value.content2, value.content3)
            if (consumeBefore.not()) {
                event.value = consumed3()
            }
        }
    }
}

@Composable
@NonRestartableComposable
fun <T1, T2, T3, T4> Event(
    event: MutableState<StateEventWithContent4<T1, T2, T3, T4>>,
    consumeBefore: Boolean = false,
    action: suspend (T1, T2, T3, T4) -> Unit
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered4<T1, T2, T3, T4>) {
            val value = event.value as StateEventWithContentTriggered4<T1, T2, T3, T4>
            if (consumeBefore) {
                event.value = consumed4()
            }
            action(value.content1, value.content2, value.content3, value.content4)
            if (consumeBefore.not()) {
                event.value = consumed4()
            }
        }
    }
}
