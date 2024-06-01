package com.glorfindel.tehanu.common.callbacks

typealias VoidCallback = () -> Unit
typealias AnyCallback<T> = (T) -> Unit
typealias ExceptionCallback = (Exception) -> Unit
