package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseStringDelegate(
    private val winehouse: Winehouse,
    private val defaultValue: String? = null,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, String?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): String? {
        val data = winehouse.getStringSync(property.name, decrypt = encrypt)
        if (data == null && defaultValue != null) {
            winehouse.storeString(defaultValue, property.name, encrypt)
            return defaultValue
        }
        return data
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: String?
    ) {
        winehouse.storeString(value, property.name, encrypt)
    }
}
