package com.glorfindel.tehanu.common.event

import androidx.compose.runtime.Immutable

@Immutable
sealed interface StateEventWithContent<out T>

@Immutable
sealed interface StateEventWithContent2<out T1, out T2>

@Immutable
sealed interface StateEventWithContent3<out T1, out T2, out T3>

@Immutable
sealed interface StateEventWithContent4<out T1, out T2, out T3, out T4>

@Immutable
class StateEventWithContentTriggered<T>(val content: T) : StateEventWithContent<T>

@Immutable
class StateEventWithContentTriggered2<T1, T2>(val content1: T1, val content2: T2) : StateEventWithContent2<T1, T2>

@Immutable
class StateEventWithContentTriggered3<T1, T2, T3>(val content1: T1, val content2: T2, val content3: T3) : StateEventWithContent3<T1, T2, T3>

@Immutable
class StateEventWithContentTriggered4<T1, T2, T3, T4>(val content1: T1, val content2: T2, val content3: T3, val content4: T4) : StateEventWithContent4<T1, T2, T3, T4>

@Immutable
class StateEventWithContentConsumed<T> : StateEventWithContent<T>

@Immutable
class StateEventWithContentConsumed2<T1, T2> : StateEventWithContent2<T1, T2>

@Immutable
class StateEventWithContentConsumed3<T1, T2, T3> : StateEventWithContent3<T1, T2, T3>

@Immutable
class StateEventWithContentConsumed4<T1, T2, T3, T4> : StateEventWithContent4<T1, T2, T3, T4>

fun <T> triggered(content: T) = StateEventWithContentTriggered(content)

fun <T1, T2> triggered(
    content1: T1,
    content2: T2
) = StateEventWithContentTriggered2(content1, content2)

fun <T1, T2, T3> triggered(
    content1: T1,
    content2: T2,
    content3: T3
) = StateEventWithContentTriggered3(content1, content2, content3)

fun <T1, T2, T3, T4> triggered(
    content1: T1,
    content2: T2,
    content3: T3,
    content4: T4
) = StateEventWithContentTriggered4(content1, content2, content3, content4)

fun <T> consumed() = StateEventWithContentConsumed<T>()

fun <T1, T2> consumed2() = StateEventWithContentConsumed2<T1, T2>()

fun <T1, T2, T3> consumed3() = StateEventWithContentConsumed3<T1, T2, T3>()

fun <T1, T2, T3, T4> consumed4() = StateEventWithContentConsumed4<T1, T2, T3, T4>()
