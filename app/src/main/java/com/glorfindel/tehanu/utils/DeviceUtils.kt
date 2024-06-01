package com.glorfindel.tehanu.utils

import android.content.Context
import android.nfc.NfcManager
import android.os.Build
import com.glorfindel.tehanu.common.enums.NfcStatus

object DeviceUtils {
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model
        } else {
            "$manufacturer $model"
        }
    }

    fun getDeviceType(): String {
        return "${Build.DEVICE} ${Build.MODEL} (${Build.PRODUCT})"
    }

    fun getDeviceOSVersion(): String {
        return "Android ${Build.VERSION.RELEASE} API ${Build.VERSION.SDK_INT}"
    }

    fun getDeviceOS(): String {
        return "Android"
    }

    fun getManufacturer() = Build.MANUFACTURER

    fun isHuawei() = Build.MANUFACTURER.equals("Huawei", ignoreCase = true)

    fun getNfcStatus(context: Context): NfcStatus {
        val adapter = (context.getSystemService(Context.NFC_SERVICE) as NfcManager).defaultAdapter
        return if (adapter != null && adapter.isEnabled) {
            NfcStatus.ENABLED
        } else if (adapter != null && adapter.isEnabled.not()) {
            NfcStatus.DISABLED
        } else {
            NfcStatus.NOT_SUPPORTED
        }
    }
}
