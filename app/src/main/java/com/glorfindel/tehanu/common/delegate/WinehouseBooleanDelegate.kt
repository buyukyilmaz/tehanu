package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseBooleanDelegate(
    private val winehouse: Winehouse,
    private val defaultValue: Boolean? = null,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, Boolean?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): Boolean? {
        val data = winehouse.getBooleanSync(property.name, decrypt = encrypt)
        if (data == null && defaultValue != null) {
            winehouse.storeBoolean(defaultValue, property.name, encrypt)
            return defaultValue
        }
        return data
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: Boolean?
    ) {
        winehouse.storeBoolean(value, property.name, encrypt)
    }
}
