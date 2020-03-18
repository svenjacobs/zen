package com.svenjacobs.zen.core.state

import kotlinx.coroutines.flow.*

/**
 * Adds a side effect on a specific selection of [State] object.
 * Ensures that values are non-null **and** distinct until changed allowing intermediate `null`
 * values.
 *
 * If the selected value becomes `null`, `onEach` is not called.
 * However the next non-null value will be handled again, even if it's equal to the last known
 * non-null value because technically there has been a change. For example:
 *
 * 1) "Hello world" -> is handled
 * 2) null -> is not handled
 * 3) "Hello world" -> is handled again
 *
 * Use [distinctSideEffect] if this behaviour is not desired.
 *
 * @see distinctSideEffect
 * @see nullableSideEffect
 * @see SelectionWithState
 */
fun <S : State, T : Any> Flow<S>.sideEffect(
    select: S.() -> T?,
    onEach: suspend SelectionWithState<T, S>.() -> Unit
) =
    map { SelectionWithState.of(it.select(), it) }
        .distinctUntilChanged()
        .filterNotNull()
        .onEach { it.onEach() }

/**
 * Adds a side effect on a specific selection of [State] object.
 * Ensures that values are non-null **and** distinct until changed.
 *
 * Similar to [sideEffect] however equal values with `null` in between won't be handled.
 *
 * For example:
 *
 * 1) "Hello world" -> is handled
 * 2) null -> is not handled
 * 3) "Hello world" -> is NOT handled
 * 4) "Hello world again" -> is handled
 *
 * @see sideEffect
 * @see nullableSideEffect
 */
fun <S : State, T : Any> Flow<S>.distinctSideEffect(
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
 * @see distinctSideEffect
 * @see SelectionWithState
 */
fun <S : State, T> Flow<S>.nullableSideEffect(
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
 * [_state] is not part of primary constructor so that the value is omitted from automatically
 * generated [equals] and [hashCode] functions in order for [Flow.distinctUntilChanged] to still
 * work properly.
 *
 * @see sideEffect
 * @see nullableSideEffect
 */
data class SelectionWithState<T, S : State>(
    val value: T
) {
    private lateinit var _state: S
    val state: S get() = _state

    internal companion object {

        internal fun <T, S : State> of(value: T?, state: S) =
            value?.let {
                SelectionWithState<T, S>(value).apply {
                    _state = state
                }
            }

        internal fun <T, S : State> ofNullable(value: T?, state: S) =
            SelectionWithState<T?, S>(value).apply {
                _state = state
            }
    }
}
