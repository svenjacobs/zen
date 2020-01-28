package com.svenjacobs.zen.core.state

/**
 * Wrapper around a [State] that permits reading and writing to it.
 */
interface StateMutator<S : State> : StateAccessor<S> {

    override var value: S?
}
