package com.glorfindel.tehanu.common.callbacks

import androidx.annotation.CallSuper

interface PermissionCallback {
    @CallSuper
    fun onPermissionGranted(reset: VoidCallback) {
        reset()
    }

    @CallSuper
    fun onPermissionDenied(reset: VoidCallback) {
        reset()
    }

    @CallSuper
    fun onNeverAskAgain(reset: VoidCallback) {
        reset()
    }
}
