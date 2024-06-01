package com.glorfindel.tehanu.infrastructure

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tehanu")

@OptIn(DelicateCoroutinesApi::class)
class Winehouse(private val context: Context, private val mapper: Mapper, private val androidKeyStore: AndroidKeyStore) {
    fun storeObject(
        data: Any?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteString(key, coroutineScope)
            return
        }
        val json = mapper.toJson(data) ?: return
        storeString(json, key, encrypt, coroutineScope)
    }

    fun storeString(
        data: String?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteString(key, coroutineScope)
            return
        }

        val text =
            if (encrypt) {
                try {
                    androidKeyStore.encrypt(data)
                } catch (e: Exception) {
                    return
                }
            } else {
                data
            }

        coroutineScope.launch {
            context.dataStore.edit { settings ->
                val preferenceKey = stringPreferencesKey(key)
                settings[preferenceKey] = text
            }
        }
    }

    fun storeInt(
        data: Int?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteInt(key, coroutineScope)
            return
        }

        if (encrypt) {
            storeString(data.toString(), key, true)
        } else {
            coroutineScope.launch {
                context.dataStore.edit { settings ->
                    val preferenceKey = intPreferencesKey(key)
                    settings[preferenceKey] = data
                }
            }
        }
    }

    fun storeLong(
        data: Long?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteLong(key, coroutineScope)
            return
        }

        if (encrypt) {
            storeString(data.toString(), key, true)
        } else {
            coroutineScope.launch {
                context.dataStore.edit { settings ->
                    val preferenceKey = longPreferencesKey(key)
                    settings[preferenceKey] = data
                }
            }
        }
    }

    fun storeDouble(
        data: Double?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteDouble(key, coroutineScope)
            return
        }

        if (encrypt) {
            storeString(data.toString(), key, true)
        } else {
            coroutineScope.launch {
                context.dataStore.edit { settings ->
                    val preferenceKey = doublePreferencesKey(key)
                    settings[preferenceKey] = data
                }
            }
        }
    }

    fun storeFloat(
        data: Float?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteFloat(key, coroutineScope)
            return
        }

        if (encrypt) {
            storeString(data.toString(), key, true)
        } else {
            coroutineScope.launch {
                context.dataStore.edit { settings ->
                    val preferenceKey = floatPreferencesKey(key)
                    settings[preferenceKey] = data
                }
            }
        }
    }

    fun storeBoolean(
        data: Boolean?,
        key: String,
        encrypt: Boolean = false,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        if (data == null) {
            deleteBoolean(key, coroutineScope)
            return
        }

        if (encrypt) {
            storeString(data.toString(), key, true)
        } else {
            coroutineScope.launch {
                context.dataStore.edit { settings ->
                    val preferenceKey = booleanPreferencesKey(key)
                    settings[preferenceKey] = data
                }
            }
        }
    }

    fun getStringSync(
        key: String,
        defaultValue: String? = null,
        decrypt: Boolean = false
    ): String? {
        val preferenceKey = stringPreferencesKey(key)
        val preferences: Preferences
        runBlocking {
            preferences = context.dataStore.data.first()
        }
        var text: String? = preferences[preferenceKey] ?: return defaultValue
        if (decrypt) {
            text =
                try {
                    androidKeyStore.decrypt(text)
                } catch (e: Exception) {
                    defaultValue
                }
        }
        return text
    }

    fun <T> getObjectSync(
        key: String,
        classOfT: Class<T>,
        decrypt: Boolean = false
    ): T? {
        val data = getStringSync(key = key, decrypt = decrypt)
        return mapper.toObject(data, classOfT)
    }

    fun getIntSync(
        key: String,
        defaultValue: Int? = null,
        decrypt: Boolean = false
    ): Int? {
        return if (decrypt) {
            getStringSync(key, defaultValue?.toString(), true)?.toInt()
        } else {
            val preferenceKey = intPreferencesKey(key)
            val preferences: Preferences
            runBlocking {
                preferences = context.dataStore.data.first()
            }
            preferences[preferenceKey] ?: defaultValue
        }
    }

    fun getLongSync(
        key: String,
        defaultValue: Long? = null,
        decrypt: Boolean = false
    ): Long? {
        return if (decrypt) {
            getStringSync(key, defaultValue?.toString(), true)?.toLong()
        } else {
            val preferenceKey = longPreferencesKey(key)
            val preferences: Preferences
            runBlocking {
                preferences = context.dataStore.data.first()
            }
            preferences[preferenceKey] ?: defaultValue
        }
    }

    fun getDoubleSync(
        key: String,
        defaultValue: Double? = null,
        decrypt: Boolean = false
    ): Double? {
        return if (decrypt) {
            getStringSync(key, defaultValue?.toString(), true)?.toDouble()
        } else {
            val preferenceKey = doublePreferencesKey(key)
            val preferences: Preferences
            runBlocking {
                preferences = context.dataStore.data.first()
            }
            preferences[preferenceKey] ?: defaultValue
        }
    }

    fun getFloatSync(
        key: String,
        defaultValue: Float? = null,
        decrypt: Boolean = false
    ): Float? {
        return if (decrypt) {
            getStringSync(key, defaultValue?.toString(), true)?.toFloat()
        } else {
            val preferenceKey = floatPreferencesKey(key)
            val preferences: Preferences
            runBlocking {
                preferences = context.dataStore.data.first()
            }
            preferences[preferenceKey] ?: defaultValue
        }
    }

    fun getBooleanSync(
        key: String,
        defaultValue: Boolean? = null,
        decrypt: Boolean = false
    ): Boolean? {
        return if (decrypt) {
            getStringSync(key, defaultValue?.toString(), true)?.toBoolean()
        } else {
            val preferenceKey = booleanPreferencesKey(key)
            val preferences: Preferences
            runBlocking {
                preferences = context.dataStore.data.first()
            }
            preferences[preferenceKey] ?: defaultValue
        }
    }

    fun getStringAsync(
        key: String,
        defaultValue: String? = null,
        decrypt: Boolean = false
    ): Flow<String?> {
        val preferenceKey = stringPreferencesKey(key)
        return context.dataStore.data.map {
            var text: String? = it[preferenceKey] ?: return@map defaultValue
            if (decrypt) {
                text =
                    try {
                        androidKeyStore.decrypt(text)
                    } catch (e: Exception) {
                        defaultValue
                    }
            }
            return@map text
        }
    }

    fun <T> getObjectAsync(
        key: String,
        classOfT: Class<T>,
        decrypt: Boolean = false
    ): Flow<T?> {
        return getStringAsync(key = key, decrypt = decrypt).map { mapper.toObject(it, classOfT) }
    }

    fun getIntAsync(
        key: String,
        defaultValue: Int? = null,
        decrypt: Boolean = false
    ): Flow<Int?> {
        if (decrypt) {
            return getStringAsync(key, defaultValue?.toString(), true).map { it?.toInt() }
        } else {
            val preferenceKey = intPreferencesKey(key)
            return context.dataStore.data.map {
                it[preferenceKey] ?: return@map defaultValue
            }
        }
    }

    fun getLongAsync(
        key: String,
        defaultValue: Long? = null,
        decrypt: Boolean = false
    ): Flow<Long?> {
        if (decrypt) {
            return getStringAsync(key, defaultValue?.toString(), true).map { it?.toLong() }
        } else {
            val preferenceKey = longPreferencesKey(key)
            return context.dataStore.data.map {
                it[preferenceKey] ?: return@map defaultValue
            }
        }
    }

    fun getDoubleAsync(
        key: String,
        defaultValue: Double? = null,
        decrypt: Boolean = false
    ): Flow<Double?> {
        if (decrypt) {
            return getStringAsync(key, defaultValue?.toString(), true).map { it?.toDouble() }
        } else {
            val preferenceKey = doublePreferencesKey(key)
            return context.dataStore.data.map {
                it[preferenceKey] ?: return@map defaultValue
            }
        }
    }

    fun getFloatAsync(
        key: String,
        defaultValue: Float? = null,
        decrypt: Boolean = false
    ): Flow<Float?> {
        if (decrypt) {
            return getStringAsync(key, defaultValue?.toString(), true).map { it?.toFloat() }
        } else {
            val preferenceKey = floatPreferencesKey(key)
            return context.dataStore.data.map {
                it[preferenceKey] ?: return@map defaultValue
            }
        }
    }

    fun getBooleanAsync(
        key: String,
        defaultValue: Boolean? = null,
        decrypt: Boolean = false
    ): Flow<Boolean?> {
        if (decrypt) {
            return getStringAsync(key, defaultValue?.toString(), true).map { it?.toBoolean() }
        } else {
            val preferenceKey = booleanPreferencesKey(key)
            return context.dataStore.data.map {
                it[preferenceKey] ?: return@map defaultValue
            }
        }
    }

    fun deleteString(
        key: String,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.remove(stringPreferencesKey(key))
            }
        }
    }

    fun deleteInt(
        key: String,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.remove(intPreferencesKey(key))
            }
        }
    }

    fun deleteLong(
        key: String,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.remove(longPreferencesKey(key))
            }
        }
    }

    fun deleteFloat(
        key: String,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.remove(floatPreferencesKey(key))
            }
        }
    }

    fun deleteDouble(
        key: String,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.remove(doublePreferencesKey(key))
            }
        }
    }

    fun deleteBoolean(
        key: String,
        coroutineScope: CoroutineScope = GlobalScope
    ) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.remove(booleanPreferencesKey(key))
            }
        }
    }

    fun deleteAll(coroutineScope: CoroutineScope = GlobalScope) {
        coroutineScope.launch {
            context.dataStore.edit {
                it.clear()
            }
        }
    }
}
