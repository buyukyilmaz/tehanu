package com.glorfindel.tehanu.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Patterns
import androidx.compose.ui.graphics.asImageBitmap
import com.glorfindel.tehanu.core.navigator.DeeplinkExtra
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun String?.containsDigit() = this?.matches(".*[0-9].*".toRegex()) ?: false

fun String?.isOnlyNumeric(): Boolean = this?.matches("\\d+".toRegex()) ?: false

fun String?.isValidEmail(): Boolean = this?.matches(Patterns.EMAIL_ADDRESS.toRegex()) ?: false

fun String?.isValidWebUrl(): Boolean = this?.matches(Patterns.WEB_URL.toRegex()) ?: false

fun String?.isValidJson(): Boolean {
    return try {
        JSONObject(this)
        true
    } catch (ex: JSONException) {
        try {
            JSONArray(this)
            true
        } catch (ex1: JSONException) {
            false
        }
    }
}

fun String?.removeSpaces() = this?.filterNot { it.isWhitespace() } ?: ""

fun String?.removeNonAlphanumericCharacters(): String {
    if (this.isNull()) return ""
    val nonAlphanumericRegex = Regex("[^A-Za-z0-9]")
    return nonAlphanumericRegex.replace(this!!, "")
}

fun String?.format(format: String): String {
    if (this.isNullOrBlank()) return ""
    val original = this.removeSpaces()
    val formatted = format.toCharArray()
    var originalIndex = 0
    for (i in formatted.indices) {
        if (formatted[i] != '#') continue
        if (originalIndex < original.length) {
            formatted[i] = original[originalIndex++]
        }
    }
    return String(formatted).replace("#", "").trim()
}

fun String.format(
    format: String,
    separator: String
): String {
    val original = this.replace(separator, "")
    val formatted = format.toCharArray()
    var originalIndex = 0
    loop@ for (i in formatted.indices) {
        if (formatted[i] != '#') continue
        if (originalIndex < original.length) {
            formatted[i] = original[originalIndex++]
        } else {
            break@loop
        }
    }
    var final = String(formatted).replace("#", "")
    while (final.endsWith(separator)) {
        final = final.removeSuffix(separator)
    }
    return final
}

fun String?.trimAll(): String {
    if (this.isNull()) return ""

    return this!!
        .trim()
        .split(" ")
        .filter { it.isBlank().not() }
        .joinToString(separator = " ") { it }
}

fun String?.base64Encode(flags: Int = Base64.NO_WRAP): String {
    if (this.isNull()) return ""

    return String(Base64.encode(this!!.toByteArray(Charsets.UTF_8), flags))
}

fun String?.base64Decode(flags: Int = Base64.NO_WRAP): String {
    if (this.isNull()) return ""

    return String(Base64.decode(this!!.toByteArray(Charsets.UTF_8), flags))
}

fun String?.getPathFromDeeplink(): String? {
    val uri =
        try {
            Uri.parse(this)
        } catch (e: Exception) {
            null
        }
    return uri?.path?.removePrefix("/")
}

fun String?.getExtraFromDeeplink(): DeeplinkExtra? {
    val uri =
        try {
            Uri.parse(this)
        } catch (e: Exception) {
            null
        }

    val query = uri?.query
    if (query.isNullOrEmpty()) return null
    val params = query.split("&")
    val map = mutableMapOf<String, String>()
    params.forEach {
        val keyValue = it.split("=")
        if (keyValue.size == 2) {
            map[keyValue[0]] = keyValue[1]
        }
    }

    return if (map.isEmpty()) null else DeeplinkExtra(map)
}

fun String.toBitmap(): Bitmap {
    val byteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun String.toImageBitmap() = toBitmap().asImageBitmap()
