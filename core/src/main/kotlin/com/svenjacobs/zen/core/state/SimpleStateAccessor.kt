package com.svenjacobs.zen.core.state

/**
 * Simple [StateAccessor] that holds a static value
 */
class SimpleStateAccessor<S : State>(
    override val value: S?
) : StateAccessor<S>
