package com.svenjacobs.zen.core.state

/**
 * Read-only wrapper around a [State].
 *
 * @see stateAccessorOf
 * @see withDefault
 */
interface StateAccessor<S : State> {

    /**
     * Returns current state value.
     *
     * Accessor method is suspending so that read operation can be synchronized via an
     * [kotlin.coroutines.CoroutineContext]. Implementations might for instance access the value as
     * follows:
     *
     * ```
     * return withContext(Dispatchers.Main.immediate) {
     *   stateHolder.value
     * }
     * ```
     */
    suspend fun get(): S?
}

/**
 * Returns a simple state accessor that holds a static value
 *
 * @see SimpleStateAccessor
 */
fun <S : State> stateAccessorOf(value: S?): StateAccessor<S> = SimpleStateAccessor(value)

/**
 * Returns a state accessor that provides a default value when state is `null`
 *
 * @see DefaultStateAccessor
 */
fun <S : State> StateAccessor<S>.withDefault(defaultValue: () -> S): DefaultStateAccessor<S> =
    DefaultStateAccessor(this, defaultValue)
