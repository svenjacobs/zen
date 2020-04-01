package com.svenjacobs.zen.core.util

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

/**
 * Utility class for bridging the gap between an asynchronous non-flow response / callback where a
 * Flow type is expected but where Flow cannot immediately be returned.
 *
 * @see asFlow
 * @see asUntypedFlow
 */
@Suppress("MemberVisibilityCanBePrivate")
class EventBroadcaster {

    @PublishedApi
    internal val channel: BroadcastChannel<Any?> = ConflatedBroadcastChannel()

    fun broadcast(event: Any?) {
        channel.offer(event)
    }

    fun close(cause: Throwable? = null) {
        channel.close(cause)
    }

    inline fun <reified T> asFlow(): Flow<T> =
        channel.asFlow().filterIsInstance()

    inline fun <reified T> asUntypedFlow(): Flow<Unit> =
        asFlow<T>().map { Unit }
}
