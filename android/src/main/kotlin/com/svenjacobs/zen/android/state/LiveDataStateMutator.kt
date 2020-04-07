package com.svenjacobs.zen.android.state

import androidx.lifecycle.MutableLiveData
import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateMutator
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LiveDataStateMutator<S : State>(
    private val state: MutableLiveData<S>,
    private val coroutineContext: CoroutineContext
) : StateMutator<S> {

    override suspend fun get(): S? =
        withContext(coroutineContext) {
            state.value
        }

    override suspend fun set(state: S?) =
        withContext(coroutineContext) {
            this@LiveDataStateMutator.state.value = state
        }
}
