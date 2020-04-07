package com.svenjacobs.zen.android.example.main.state

import com.svenjacobs.zen.android.state.StateViewModel

class MainViewModel(
    @Volatile override var state: MainState? = null
) : StateViewModel<MainState>()
