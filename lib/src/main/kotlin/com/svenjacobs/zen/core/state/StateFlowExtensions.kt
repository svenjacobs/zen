package com.svenjacobs.zen.core.state

import kotlinx.coroutines.flow.*

/**
 * Adds a side effect on a specific selection of [State] object.
 * Ensures that values are non-null and distinct until changed.
 *
 * @see nullableSideEffect
 * @see SelectionWithState
 */
suspend fun <S : State, T : Any> Flow<S>.sideEffect(
    select: S.() -> T?,
    onEach: suspend SelectionWithState<T, S>.() -> Unit
) =
    mapNotNull { SelectionWithState.of(it.select(), it) }
        .distinctUntilChanged()
        .onEach { it.onEach() }

/**
 * Adds a side effect on a specific selection of [State] object.
 * Ensures that values are distinct until changed.
 *
 * @see sideEffect
 * @see SelectionWithState
 */
suspend fun <S : State, T> Flow<S>.nullableSideEffect(
    select: S.() -> T?,
    onEach: suspend SelectionWithState<T?, S>.() -> Unit
) =
    map { SelectionWithState.ofNullable(it.select(), it) }
        .distinctUntilChanged()
        .onEach { it.onEach() }

/**
 * Holds selected value of [sideEffect] or [nullableSideEffect] and provides current [State] as
 * an additional property.
 *
 * [state] is not part of primary constructor so that the value is omitted from automatically
 * generated [equals] and [hashCode] functions in order for [Flow.distinctUntilChanged] to still
 * work properly.
 *
 * @see sideEffect
 * @see nullableSideEffect
 */
data class SelectionWithState<T, S : State>(
    val value: T
) {
    lateinit var state: S

    internal companion object {

        internal fun <T, S : State> of(value: T?, state: S) =
            value?.let {
                SelectionWithState<T, S>(value).apply {
                    this.state = state
                }
            }

        internal fun <T, S : State> ofNullable(value: T?, state: S) =
            SelectionWithState<T?, S>(value).apply {
                this.state = state
            }
    }
}
