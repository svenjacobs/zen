package com.svenjacobs.zen.common.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Emits a default value if original Flow has not emitted any value after [defaultAfter] milliseconds.
 */
fun <T> Flow<T>.delayedDefault(
    defaultAfter: Long,
    defaultValue: suspend () -> T
): Flow<T> =
    channelFlow {
        val job = launch {
            delay(defaultAfter)
            send(defaultValue())
        }

        collect {
            job.cancel()
            send(it)
        }

        // Cancel (again) in case of empty Flow
        job.cancel()
    }
