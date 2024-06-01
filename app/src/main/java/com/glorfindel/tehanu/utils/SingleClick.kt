package com.glorfindel.tehanu.utils

import com.glorfindel.tehanu.core.TehanuDefaults

class SingleClick private constructor() {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    private fun clickOnce(
        event: () -> Unit,
        defaultDelay: Long
    ) {
        if (now - lastEventTimeMs >= defaultDelay) {
            lastEventTimeMs = now
            event.invoke()
        }
    }

    companion object {
        @Volatile
        private var instance: SingleClick? = null

        private fun getInstance() = instance ?: synchronized(this) { instance ?: SingleClick().also { instance = it } }

        fun clickOnce(
            event: () -> Unit,
            defaultDelay: Long = TehanuDefaults.Common.singleClickDelay
        ) {
            getInstance().clickOnce(event, defaultDelay)
        }
    }
}
