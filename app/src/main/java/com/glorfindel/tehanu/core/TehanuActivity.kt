package com.glorfindel.tehanu.core

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.glorfindel.tehanu.common.callbacks.PermissionCallback

open class TehanuActivity : AppCompatActivity() {
    private var permissionCallback: PermissionCallback? = null
    private val singlePermissionResultCode = 20000

    fun hasPermission(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(
        permission: String,
        permissionCallback: PermissionCallback
    ) {
        this.permissionCallback = permissionCallback
        if (hasPermission(permission)) {
            this.permissionCallback?.onPermissionGranted { this.permissionCallback = null }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), singlePermissionResultCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != singlePermissionResultCode) return

        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            permissionCallback?.onPermissionGranted { this.permissionCallback = null }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                permissionCallback?.onPermissionDenied { this.permissionCallback = null }
            } else {
                permissionCallback?.onNeverAskAgain { this.permissionCallback = null }
            }
        }
    }
}
