package com.svenjacobs.zen.android.master

import android.util.Log
import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.state.State

class AndroidLoggingMiddleware<A : Action, S : State>(
    private val priority: Int = Log.DEBUG
) : ZenMaster.Middleware<A, S> {

    override suspend fun onAction(action: A): A {
        Log.println(priority, TAG, "onAction: $action")
        return action
    }

    override suspend fun onState(state: S): S {
        Log.println(priority, TAG, "onState: $state")
        return state
    }

    private companion object {
        private const val TAG = "ZEN"
    }
}
