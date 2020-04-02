package com.svenjacobs.zen.android.state

import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateMutator

class StateViewModelStateMutator<S : State>(
    private val viewModel: StateViewModel<S>
) : StateMutator<S> {

    override var value: S?
        get() = viewModel.state
        set(value) {
            viewModel.state = value
        }
}
