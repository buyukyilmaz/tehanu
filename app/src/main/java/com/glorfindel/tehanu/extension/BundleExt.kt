package com.glorfindel.tehanu.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun <T> Bundle.put(
    key: String,
    value: T
) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Short -> putShort(key, value)
        is Long -> putLong(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalStateException("Type of property $key is not supported")
    }
}

fun <T : Serializable> Bundle?.serializable(
    key: String,
    clazz: Class<T>
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getSerializable(key, clazz)
    } else {
        this?.getSerializable(key) as? T
    }
}

fun <T : Parcelable> Bundle?.parcelable(
    key: String,
    clazz: Class<T>
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getParcelable(key, clazz)
    } else {
        this?.getParcelable(key) as? T
    }
}

fun <T : Parcelable> Bundle?.parcelableList(
    key: String,
    clazz: Class<T>
): List<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getParcelableArrayList(key, clazz)
    } else {
        this?.getParcelableArrayList(key)
    }
}
