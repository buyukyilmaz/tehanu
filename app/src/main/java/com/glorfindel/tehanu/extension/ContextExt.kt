package com.glorfindel.tehanu.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.glorfindel.tehanu.core.TehanuActivity
import java.io.Serializable

fun Context.getComponentActivity(): ComponentActivity =
    when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getComponentActivity()
        else -> throw RuntimeException("can't find ComponentActivity")
    }

fun Context.getAppCompatActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> baseContext.getAppCompatActivity()
        else -> throw RuntimeException("can't find AppCompatActivity")
    }

fun Context.getTehanuActivity(): TehanuActivity =
    when (this) {
        is TehanuActivity -> this
        is ContextWrapper -> baseContext.getTehanuActivity()
        else -> throw RuntimeException("can't find TehanuActivity")
    }

fun Context.getSupportFragmentManager() = getAppCompatActivity().supportFragmentManager

inline fun <reified T : Parcelable> Context.getParcelableFromIntent(key: String): T? = getComponentActivity().intent.parcelable(key, T::class.java)

inline fun <reified T : Parcelable> Context.getParcelableListFromIntent(key: String): List<T?>? = getComponentActivity().intent.parcelableList(key, T::class.java)

inline fun <reified T : Serializable> Context.getSerializableFromIntent(key: String): T? = getComponentActivity().intent.serializable(key, T::class.java)

fun Context.getStringFromIntent(key: String) = getComponentActivity().intent.extras?.getString(key)

fun Context.getIntFromIntent(key: String) = getComponentActivity().intent.extras?.getInt(key)

fun Context.getBooleanFromIntent(key: String) = getComponentActivity().intent.extras?.getBoolean(key)

fun Context.getFloatFromIntent(key: String) = getComponentActivity().intent.extras?.getFloat(key)

fun Context.getDoubleFromIntent(key: String) = getComponentActivity().intent.extras?.getDouble(key)

fun Context.getLongFromIntent(key: String) = getComponentActivity().intent.extras?.getLong(key)

fun Context.openPlayStore(packageName: String = this.packageName) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (anfe: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}
