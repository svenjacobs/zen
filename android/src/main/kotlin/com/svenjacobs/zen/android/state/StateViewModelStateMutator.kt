package com.svenjacobs.zen.android.state

import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateMutator
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class StateViewModelStateMutator<S : State>(
    private val viewModel: StateViewModel<S>,
    private val coroutineContext: CoroutineContext
) : StateMutator<S> {

    override suspend fun get(): S? =
        withContext(coroutineContext) {
            viewModel.state
        }

    override suspend fun set(state: S?) =
        withContext(coroutineContext) {
            viewModel.state = state
        }
}
