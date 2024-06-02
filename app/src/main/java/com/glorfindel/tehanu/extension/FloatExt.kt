package com.glorfindel.tehanu.extension

fun Float?.isInteger() = this?.rem(1f) == 0f

fun Float?.lessThan(value: Float) = this != null && this < value

fun Float?.greaterThan(value: Float) = this != null && this > value
