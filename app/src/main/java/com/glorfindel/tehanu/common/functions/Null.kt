package com.glorfindel.tehanu.common.functions

fun <T1, T2> checkNull(
    data: T1?,
    block: (T1) -> T2?
): T2? {
    if (data == null) return null
    return block(data)
}
