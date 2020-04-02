package com.svenjacobs.zen.android.state

import androidx.lifecycle.MutableLiveData
import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateMutator

class LiveDataStateMutator<S : State>(
    private val state: MutableLiveData<S>
) : StateMutator<S> {

    override var value: S?
        get() = state.value
        set(value) {
            state.value = value
        }
}
