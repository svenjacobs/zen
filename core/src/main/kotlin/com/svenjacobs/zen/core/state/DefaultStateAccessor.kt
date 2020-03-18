package com.svenjacobs.zen.core.state

import kotlin.reflect.KProperty

/**
 * Accessor that provides a default value if original value is `null`.
 *
 * @see StateAccessor
 */
class DefaultStateAccessor<S : State>(
    private val accessor: StateAccessor<S>,
    private val defaultValue: () -> S
) : StateAccessor<S> {

    override val value: S
        get() = accessor.value ?: defaultValue()

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): S =
        value
}
