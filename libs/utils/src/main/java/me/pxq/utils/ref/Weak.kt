package me.pxq.utils.ref

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/30 9:40 PM
 */

class Weak<T : Any>(initializer: () -> T?) {
    private var weakReference = WeakReference<T?>(initializer())

    constructor() : this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = WeakReference(value)
    }
}

