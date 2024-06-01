package com.glorfindel.tehanu.common.event

import androidx.compose.runtime.Immutable

@Immutable
sealed interface StateEvent {
    @Immutable
    data object Triggered : StateEvent

    @Immutable
    data object Consumed : StateEvent
}

val triggered = StateEvent.Triggered

val consumed = StateEvent.Consumed
