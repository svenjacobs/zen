package com.svenjacobs.zen.core.master

import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.master.ZenMaster.Contract
import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateMutator
import com.svenjacobs.zen.core.state.Transformer
import com.svenjacobs.zen.core.view.ZenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * A ZenMaster receives a [Flow] of [Action], transforming them into state (changes) and finally
 * delegating each state to [Contract.stateChanges].
 *
 * The [Contract] declares the interaction between master and view.
 */
interface ZenMaster {

    interface Contract<in V : ZenView, out A : Action, in S : State> {

        /**
         * Permits initialization after view is ready.
         * Suspending function is called in coroutine scope of view.
         */
        suspend fun onViewReady(view: V) {}

        /**
         * Returns [Flow] of incoming [Actions][Action].
         *
         * Implementation will presumably map one or multiple Flows of UI events into a single Flow
         * of actions.
         */
        fun actions(view: V): Flow<A>

        /**
         * Handles [State] changes, adding side effects to Flow that perform related functions on [view].
         */
        suspend fun stateChanges(state: Flow<S>, view: V): Flow<Any?>
    }

    fun onViewReady()
}

class ZenMasterImpl<in V : ZenView, in A : Action, in S : State>(
    private val view: V,
    private val viewCoroutineScopeProvider: () -> CoroutineScope,
    private val transformer: Transformer<A, S>,
    private val contract: Contract<V, A, S>,
    private val state: StateMutator<S>,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : ZenMaster {

    /**
     * Should be called when view is ready.
     *
     * Will pass [Contract.actions] to [Transformer] for transformation, afterwards delegating
     * transformed Flow of [State] to [Contract.stateChanges].
     */
    override fun onViewReady() {
        viewCoroutineScopeProvider().let { scope ->
            scope.launch { contract.onViewReady(view) }
            val actions = contract.actions(view).flowOn(uiContext)
            val state = transformer.transform(actions)
                .onEach { state.value = it }
                .flowOn(uiContext)
                // broadcastIn() & asFlow() is used here to create a "shared" state flow
                // that is unbundled from further downstream transformations so that collection is
                // only performed once.
                // This should be replaced by share() operator once available:
                // https://github.com/Kotlin/kotlinx.coroutines/issues/1261
                .broadcastIn(scope)
                .asFlow()
            scope.launch { contract.stateChanges(state, view).collect() }
        }
    }
}
