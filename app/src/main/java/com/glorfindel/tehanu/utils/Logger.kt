package com.glorfindel.tehanu.utils

import android.util.Log
import com.glorfindel.tehanu.core.TehanuDefaults

private fun log(
    log: Any?,
    priority: Int,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    if (overrideLogCondition.not() && TehanuDefaults.Logger.showLog.not()) return

    Log.println(priority, tag, System.lineSeparator())
    Log.println(priority, tag, "────────────────────────────────────────────────────────")
    Log.println(priority, tag, "Thread: ${Thread.currentThread().name}")
    Log.println(priority, tag, System.lineSeparator())
    Log.println(priority, tag, "Log   : ${log?.toString() ?: "null"}")
    Log.println(priority, tag, "────────────────────────────────────────────────────────")
    Log.println(priority, tag, System.lineSeparator())
}

fun logInfo(
    log: Any?,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    log(log, Log.INFO, tag, overrideLogCondition)
}

fun logError(
    log: Any?,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    log(log, Log.ERROR, tag, overrideLogCondition)
}

fun logWarn(
    log: Any?,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    log(log, Log.WARN, tag, overrideLogCondition)
}

fun logAssert(
    log: Any?,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    log(log, Log.ASSERT, tag, overrideLogCondition)
}

fun logDebug(
    log: Any?,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    log(log, Log.DEBUG, tag, overrideLogCondition)
}

fun logVerbose(
    log: Any?,
    tag: String = TehanuDefaults.Logger.tag,
    overrideLogCondition: Boolean = false
) {
    log(log, Log.VERBOSE, tag, overrideLogCondition)
}
