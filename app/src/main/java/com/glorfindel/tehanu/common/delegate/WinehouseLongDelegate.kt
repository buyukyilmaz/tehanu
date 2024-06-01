package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseLongDelegate(
    private val winehouse: Winehouse,
    private val defaultValue: Long? = null,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, Long?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): Long? {
        val data = winehouse.getLongSync(property.name, decrypt = encrypt)
        if (data == null && defaultValue != null) {
            winehouse.storeLong(defaultValue, property.name, encrypt)
            return defaultValue
        }
        return data
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: Long?
    ) {
        winehouse.storeLong(value, property.name, encrypt)
    }
}
