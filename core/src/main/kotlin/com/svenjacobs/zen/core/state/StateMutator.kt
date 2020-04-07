package com.svenjacobs.zen.core.state

/**
 * Wrapper around a [State] that permits reading and writing to it.
 */
interface StateMutator<S : State> : StateAccessor<S> {

    /**
     * Sets current state value.
     *
     * Mutator method is suspending so that write operation can be synchronized via an
     * [kotlin.coroutines.CoroutineContext]. Implementations might for instance write the value as
     * follows:
     *
     * ```
     * withContext(Dispatchers.Main.immediate) {
     *   stateHolder.value = state
     * }
     * ```
     **/
    suspend fun set(state: S?)
}
