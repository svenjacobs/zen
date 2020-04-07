package com.svenjacobs.zen.core.state

/**
 * Accessor that provides a default value if original value is `null`.
 *
 * @see StateAccessor
 */
class DefaultStateAccessor<S : State>(
    private val accessor: StateAccessor<S>,
    private val defaultValue: () -> S
) : StateAccessor<S> {

    override suspend fun get(): S =
        accessor.get() ?: defaultValue()
}
