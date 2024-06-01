package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseDoubleDelegate(
    private val winehouse: Winehouse,
    private val defaultValue: Double? = null,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, Double?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): Double? {
        val data = winehouse.getDoubleSync(property.name, decrypt = encrypt)
        if (data == null && defaultValue != null) {
            winehouse.storeDouble(defaultValue, property.name, encrypt)
            return defaultValue
        }
        return data
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: Double?
    ) {
        winehouse.storeDouble(value, property.name, encrypt)
    }
}
