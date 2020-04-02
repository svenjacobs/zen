package com.svenjacobs.zen.android.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.svenjacobs.zen.core.state.State

abstract class LiveDataStateViewModel<S : State> : ViewModel() {

    abstract val state: MutableLiveData<S>
}
