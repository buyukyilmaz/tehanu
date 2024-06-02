package com.glorfindel.tehanu.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeyValue(val key: String, val value: String) : Parcelable
