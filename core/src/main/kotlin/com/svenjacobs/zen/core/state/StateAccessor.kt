package com.svenjacobs.zen.core.state

import kotlin.reflect.KProperty

/**
 * Read-only wrapper around a [State].
 *
 * @see withDefault
 */
interface StateAccessor<S : State> {

    val value: S?

    operator fun getValue(thisRef: Any?, property: KProperty<*>): S? = value
}

/**
 * Returns a state accessor that provides a default value when state is `null`
 *
 * @see DefaultStateAccessor
 */
fun <S : State> StateAccessor<S>.withDefault(defaultValue: () -> S) =
    DefaultStateAccessor(this, defaultValue)
