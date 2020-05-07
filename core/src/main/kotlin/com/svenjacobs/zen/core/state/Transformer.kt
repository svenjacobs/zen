package com.svenjacobs.zen.core.state

import com.svenjacobs.zen.common.coroutines.flow.flatMapDistinct
import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.state.Transformer.Transformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * Transforms [Flow] of [Action] into Flow of [State].
 *
 * The transformer's duty is to transform incoming actions into a state, likely fetching, computing
 * and converting data through use cases and mappers. Transformations are described via [Transformation].
 *
 * In terms of Flux/Redux this would be the Reducer.
 *
 * @param transformationContext A [CoroutineContext] where [Transformation.invoke] is executed in
 *
 * @see com.svenjacobs.zen.core.master.ZenMaster
 * @see Transformation
 */
class Transformer<in A : Action, out S : State>(
    private val state: StateAccessor<S>,
    private val transformation: Transformation<A, S>,
    private val transformationContext: CoroutineContext = Dispatchers.Default
) {

    interface Transformation<in A : Action, S : State> {

        /**
         * Return [Flow] of [State] based on given action.
         *
         * Flow might emit multiple values, like a loading state followed by a content or error state.
         *
         * Note that Flows produced by Actions are distinct and that a previous Flow of an incoming
         * Action is cancelled. So there is only one active Flow per Action. For more details see
         * [flatMapDistinct].
         *
         * @param action [Action] that was dispatched
         * @param state Provides access to current value of [State]. State might be `null` initially.
         */
        suspend operator fun invoke(action: A, state: StateAccessor<S>): Flow<S>
    }

    fun transform(actions: Flow<A>): Flow<S> =
        actions
            .flatMapDistinct(
                distinctBy = { it.id },
                transform = { transformation(it, state) }
            )
            .flowOn(transformationContext)
}
