package com.glorfindel.tehanu.extension

fun Double?.isInteger() = this?.rem(1.0) == 0.0
