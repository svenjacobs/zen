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
import kotlin.coroutines.CoroutineContext

/**
 * A ZenMaster receives a [Flow] of [Action], transforming them into state (changes) (via [Transformer]) and finally
 * delegating each state to [Contract.stateChanges].
 *
 * The [Contract] declares the interaction between master and view.
 *
 * @see Contract
 * @see Transformer
 */
interface ZenMaster {

    interface Contract<in V : ZenView, out A : Action, in S : State> {

        /**
         * Allows initialization of contract after view is ready.
         *
         * Is called before [actions] and [stateChanges].
         *
         * The context (`this`) of the function is the coroutine scope of the view.
         * This allows launching coroutines if necessary.
         */
        fun CoroutineScope.onViewReady(view: V) {}

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
        fun stateChanges(state: Flow<S>, view: V): Flow<Any?>
    }

    interface Middleware<A : Action, S : State> {

        suspend fun onAction(action: A): A

        suspend fun onState(state: S): S
    }

    fun onViewReady()
}

/**
 * @param viewCoroutineScope A [CoroutineScope] that is attached to the lifecycle of the view
 * @param uiContext A [CoroutineContext] where UI operations should be performed in
 */
class ZenMasterImpl<in V : ZenView, A : Action, S : State>(
    private val view: V,
    private val contract: Contract<V, A, S>,
    private val transformer: Transformer<A, S>,
    private val state: StateMutator<S>,
    private val viewCoroutineScope: CoroutineScope,
    private val uiContext: CoroutineContext = Dispatchers.Main.immediate,
    private val middleware: ZenMaster.Middleware<A, S> = NopMiddleware()
) : ZenMaster {

    /**
     * Should be called when view is ready.
     *
     * Will pass [Contract.actions] to [Transformer] for transformation, afterwards delegating
     * transformed Flow of [State] to [Contract.stateChanges].
     */
    override fun onViewReady() {
        with(contract) { viewCoroutineScope.onViewReady(view) }

        val actions = contract.actions(view).map(middleware::onAction).flowOn(uiContext)
        val state = transformer.transform(actions)
            .map(middleware::onState)
            .onEach { state.value = it }
            .flowOn(uiContext)
            // broadcastIn() & asFlow() is used here to create a "shared" state flow
            // that is unbundled from further downstream transformations so that collection is
            // only performed once.
            // This should be replaced by share() operator once available:
            // https://github.com/Kotlin/kotlinx.coroutines/issues/1261
            .broadcastIn(viewCoroutineScope)
            .asFlow()

        contract.stateChanges(state, view).launchIn(viewCoroutineScope)
    }
}
