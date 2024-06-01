package com.glorfindel.tehanu.extension

fun Int?.isEven() = this?.rem(2) == 0

fun Int?.isOdd() = this?.rem(2) == 1
