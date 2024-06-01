package com.glorfindel.tehanu.infrastructure

import com.glorfindel.tehanu.common.functions.tryOrNull
import com.google.gson.GsonBuilder

class Mapper {
    private val gson = GsonBuilder().create()

    fun <T> toObject(
        json: String?,
        classOfT: Class<T>
    ): T? = tryOrNull { gson.fromJson(json, classOfT) }

    fun toJson(value: Any?): String? = tryOrNull { gson.toJson(value) }
}
