package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseFloatDelegate(
    private val winehouse: Winehouse,
    private val defaultValue: Float? = null,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, Float?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): Float? {
        val data = winehouse.getFloatSync(property.name, decrypt = encrypt)
        if (data == null && defaultValue != null) {
            winehouse.storeFloat(defaultValue, property.name, encrypt)
            return defaultValue
        }
        return data
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: Float?
    ) {
        winehouse.storeFloat(value, property.name, encrypt)
    }
}
