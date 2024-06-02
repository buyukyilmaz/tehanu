package com.glorfindel.tehanu.extension

fun Long?.isEven() = this?.rem(2L) == 0L

fun Long?.isOdd() = this?.rem(2L) == 1L

fun Long?.lessThan(value: Long) = this != null && this < value

fun Long?.greaterThan(value: Long) = this != null && this > value
