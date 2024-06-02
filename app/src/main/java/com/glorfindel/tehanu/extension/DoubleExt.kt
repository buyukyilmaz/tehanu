package com.glorfindel.tehanu.extension

fun Double?.isInteger() = this?.rem(1.0) == 0.0

fun Double?.lessThan(value: Double) = this != null && this < value

fun Double?.greaterThan(value: Double) = this != null && this > value
