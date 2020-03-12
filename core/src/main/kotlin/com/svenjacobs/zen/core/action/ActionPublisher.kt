package com.svenjacobs.zen.core.action

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge

@Suppress("MemberVisibilityCanBePrivate")
class ActionPublisher<A : Action> {

    internal var actions: Flow<A> = emptyFlow()

    fun publish(actions: Flow<A>) {
        this.actions = merge(this.actions, actions)
    }

    /**
     * Shorthand for `publish(flowOf(action))`
     */
    fun publish(action: A) {
        publish(flowOf(action))
    }
}
