package com.glorfindel.tehanu.extension

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable

fun <T : Serializable> Intent.serializable(
    key: String,
    clazz: Class<T>
) = extras?.serializable(key, clazz)

fun <T : Parcelable> Intent.parcelable(
    key: String,
    clazz: Class<T>
) = extras?.parcelable(key, clazz)

fun <T : Parcelable> Intent.parcelableList(
    key: String,
    clazz: Class<T>
) = extras?.parcelableList(key, clazz)
