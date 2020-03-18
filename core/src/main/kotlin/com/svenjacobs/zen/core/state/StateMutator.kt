package com.svenjacobs.zen.core.state

import kotlin.reflect.KProperty

/**
 * Wrapper around a [State] that permits reading and writing to it.
 */
interface StateMutator<S : State> : StateAccessor<S> {

    override var value: S?

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: S?) {
        this.value = value
    }
}
