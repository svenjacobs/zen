package com.svenjacobs.zen.core.state

import com.svenjacobs.zen.core.action.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext

/**
 * Transforms [Flow] of [Action] into Flow of [State] that is delegated to [StateMutator].
 *
 * The transformers's duty is to transform incoming actions into a state, likely fetching, computing
 * and converting data through use cases and mappers.
 *
 * In terms of Flux/Redux this would be the Reducer.
 */
class Transformer<in A : Action, out S : State>(
    private val state: StateMutator<S>,
    private val transformation: Transformation<A, S>,
    private val context: CoroutineContext
) {

    interface Transformation<in A : Action, S : State> {

        /**
         * Return [Flow] of [State] based on given action.
         *
         * Flow might emit multiple values, like a loading state followed by a content or error state.
         *
         * @param action [Action] that was dispatched
         * @param currentState Current value of [State]. Might be `null` initially.
         */
        suspend operator fun invoke(action: A, currentState: S?): Flow<S>
    }

    fun transform(actions: Flow<A>): Flow<S> =
        actions.flatMapMerge { transformation(it, state.value) }
            .onEach { state.value = it }
            .flowOn(context)
}
