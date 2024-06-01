package com.glorfindel.tehanu.common.delegate

import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WinehouseObjectDelegate<T : Any?>(
    private val winehouse: Winehouse,
    private val classOfT: Class<T>,
    private val encrypt: Boolean = false
) : ReadWriteProperty<Any, T?> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): T? {
        return winehouse.getObjectSync(property.name, classOfT, encrypt)
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: T?
    ) {
        winehouse.storeObject(value, property.name, encrypt)
    }
}
