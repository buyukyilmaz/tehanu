package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseIntDelegate(
    private val winehouse: Winehouse,
    private val defaultValue: Int? = null,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, Int?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): Int? {
        val data = winehouse.getIntSync(property.name, decrypt = encrypt)
        if (data == null && defaultValue != null) {
            winehouse.storeInt(defaultValue, property.name, encrypt)
            return defaultValue
        }
        return data
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: Int?
    ) {
        winehouse.storeInt(value, property.name, encrypt)
    }
}
