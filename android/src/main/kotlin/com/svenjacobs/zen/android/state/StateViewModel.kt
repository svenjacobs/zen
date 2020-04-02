package com.svenjacobs.zen.android.state

import androidx.lifecycle.ViewModel
import com.svenjacobs.zen.core.state.State

abstract class StateViewModel<S : State> : ViewModel() {

    abstract var state: S?
}
