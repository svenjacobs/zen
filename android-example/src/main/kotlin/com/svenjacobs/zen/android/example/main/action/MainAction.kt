package com.svenjacobs.zen.android.example.main.action

import com.svenjacobs.zen.core.action.Action

sealed class MainAction : Action {

    object LoadAction : MainAction()
}
