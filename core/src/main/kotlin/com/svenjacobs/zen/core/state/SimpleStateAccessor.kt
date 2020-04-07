package com.svenjacobs.zen.core.state

/**
 * Simple [StateAccessor] that holds a static value
 */
class SimpleStateAccessor<S : State>(
    val state: S?
) : StateAccessor<S> {

    override suspend fun get(): S? = state
}
