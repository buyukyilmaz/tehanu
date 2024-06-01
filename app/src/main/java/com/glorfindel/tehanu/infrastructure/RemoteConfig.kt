package com.glorfindel.tehanu.infrastructure

import com.glorfindel.tehanu.common.functions.tryOrDefault

class RemoteConfig {
    private val configs = mutableMapOf<String, Any?>()
    private var hash: String? = null

    fun get(key: String): Any? = configs[key]

    fun getString(
        key: String,
        default: String? = null
    ) = tryOrDefault(default) { configs[key] as? String }

    fun getBoolean(
        key: String,
        default: Boolean? = null
    ) = tryOrDefault(default) { configs[key] as? Boolean }

    fun getDouble(
        key: String,
        default: Double? = null
    ) = tryOrDefault(default) { configs[key] as? Double }

    fun getFloat(
        key: String,
        default: Float? = null
    ) = tryOrDefault(default) { configs[key] as? Float }

    fun getInt(
        key: String,
        default: Int? = null
    ) = tryOrDefault(default) { configs[key] as? Int }

    fun getLong(
        key: String,
        default: Long? = null
    ) = tryOrDefault(default) { configs[key] as? Long }

    fun clear() {
        configs.clear()
        hash = null
    }

    fun setConfigs(
        configs: MutableMap<String, Any?>,
        hash: String? = null
    ) {
        clear()
        this.configs.putAll(configs)
        this.hash = hash
    }

    fun getHash() = hash

    fun getConfigs() = configs
}
