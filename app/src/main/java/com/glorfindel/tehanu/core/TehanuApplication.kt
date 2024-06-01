package com.glorfindel.tehanu.core

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner

open class TehanuApplication : Application() {
    var isAppForegrounded = false

    open fun onAppBackgrounded() {}

    open fun onAppForegrounded() {}

    override fun onCreate() {
        super.onCreate()
        val lifecycleEventObserver =
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        isAppForegrounded = false
                        onAppBackgrounded()
                    }

                    Lifecycle.Event.ON_START -> {
                        isAppForegrounded = true
                        onAppForegrounded()
                    }

                    else -> {}
                }
            }
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
    }
}
