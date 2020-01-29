package com.svenjacobs.zen.core.state

/**
 * Read-only wrapper around a [State].
 */
interface StateAccessor<S : State> {

    val value: S?
}
