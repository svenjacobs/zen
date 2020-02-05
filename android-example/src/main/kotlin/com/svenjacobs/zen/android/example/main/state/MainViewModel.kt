package com.svenjacobs.zen.android.example.main.state

import androidx.lifecycle.MutableLiveData
import com.svenjacobs.zen.android.state.StateViewModel

class MainViewModel(
    override val state: MutableLiveData<MainState> = MutableLiveData()
) : StateViewModel<MainState>()
