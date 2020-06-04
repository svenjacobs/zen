package com.svenjacobs.zen.core.master

import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.action.ActionPublisher
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
 * Receives a [Flow] of [Action], transforms it into state (changes) (via [Transformer]) and
 * finally delegates each state to [Contract.stateChanges].
 *
 * @see Contract
 * @see Transformer
 */
interface ZenMaster {

    /**
     * Describes interaction/relation between master and view.
     */
    interface Contract<in V : ZenView, A : Action, in S : State> {

        /**
         * Allows initialization of contract after view is ready.
         *
         * Is called before [actions] and [stateChanges].
         *
         * The context (`this`) of the function is the coroutine scope of the view.
         * This allows launching coroutines if necessary.
         *
         * @param publisher Enables optional publication of flow emissions (for instance an `OnViewReady` action)
         */
        fun CoroutineScope.onViewReady(view: V, publisher: ActionPublisher<A>) {}

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

    /**
     * Notifies ZenMaster that view is ready for Flow transformation.
     *
     * @param viewCoroutineScope CoroutineScope that is attached to the lifecycle of the view
     */
    fun onViewReady(viewCoroutineScope: CoroutineScope)
}

/**
 * @param uiContext A [CoroutineContext] where UI operations should be performed in
 * @param exceptionHandler Exception handler that handles all uncaught exceptions of Flow. Default implementation just rethrows exception.
 *
 * @see ZenMaster
 */
class ZenMasterImpl<in V : ZenView, A : Action, S : State>(
    private val view: V,
    private val contract: Contract<V, A, S>,
    private val transformer: Transformer<A, S>,
    private val state: StateMutator<S>,
    private val uiContext: CoroutineContext = Dispatchers.Main.immediate,
    private val middleware: ZenMaster.Middleware<A, S> = NopMiddleware(),
    private val exceptionHandler: suspend FlowCollector<S>.(Throwable) -> Unit = { e -> throw e }
) : ZenMaster {

    /**
     * Notifies ZenMaster that view is ready for Flow transformation.
     *
     * Passes [Contract.actions] to [Transformer] for transformation, afterwards delegates
     * transformed Flow of [State] to [Contract.stateChanges].
     *
     * @param viewCoroutineScope CoroutineScope that is attached to the lifecycle of the view
     */
    override fun onViewReady(viewCoroutineScope: CoroutineScope) {
        val publisher = ActionPublisher<A>()

        with(contract) { viewCoroutineScope.onViewReady(view, publisher) }

        val actions = merge(
            publisher.actions,
            contract.actions(view)
        )
            .map(middleware::onAction)
            .flowOn(uiContext)

        val state = transformer.transform(actions)
            .map(middleware::onState)
            .onEach { state.set(it) }
            .flowOn(uiContext)
            // broadcastIn() & asFlow() is used here to create a "shared" state flow
            // that is unbundled from further downstream transformations so that collection is
            // only performed once.
            // This should be replaced by share() operator once available:
            // https://github.com/Kotlin/kotlinx.coroutines/issues/1261
            .broadcastIn(viewCoroutineScope)
            .asFlow()

        contract.stateChanges(state, view)
            .catch(exceptionHandler)
            .launchIn(viewCoroutineScope)
    }
}
