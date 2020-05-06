package com.svenjacobs.zen.common.coroutines.flow

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Flattens Flow by collecting each resulting Flow of incoming, transformed value `T`.
 *
 * Previous Flow collections are cancelled for each incoming, unique value `T` where distinction is
 * defined by [distinctBy].
 *
 * Flow collections are cancelled if original Flow terminates or throws exception.
 *
 * For example if transformation of value `A` returns a Flow that constantly produces values,
 * the next value of `A` cancels previous Flow collection and resulting, merged Flow will only
 * emit values of new Flow (along with Flows of other, unique values).
 */
fun <T : Any, R> Flow<T>.flatMapDistinct(
    context: CoroutineContext = EmptyCoroutineContext,
    distinctBy: (T) -> Any = { it },
    transform: suspend (T) -> Flow<R>
): Flow<R> = channelFlow {
    supervisorScope {
        withContext(context) {
            val jobs = mutableMapOf<Any, Job>()

            try {
                collect { value ->
                    val key = distinctBy(value)
                    val flow = transform(value)

                    jobs[key]?.cancel()
                    jobs[key] = launch {
                        flow.collect { innerValue ->
                            runCatching {
                                send(innerValue)
                            }
                        }
                    }
                }
            } finally {
                jobs.values.forEach { job -> job.cancel() }
            }
        }
    }
}
