package com.svenjacobs.zen.core.master

import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.state.State

class NopMiddleware<A : Action, S : State> : ZenMaster.Middleware<A, S> {

    override suspend fun onAction(action: A) = action

    override suspend fun onState(state: S) = state
}
