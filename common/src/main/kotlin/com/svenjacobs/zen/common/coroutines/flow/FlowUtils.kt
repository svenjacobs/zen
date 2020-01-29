package com.svenjacobs.zen.common.coroutines.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

/**
 * Boxes given value in a [Flow] unless it's already a Flow.
 * If [nullToEmpty] is true and value is `null`, an empty Flow will be returned.
 */
@Suppress("UNCHECKED_CAST")
fun <T> boxInFlow(value: Any?, nullToEmpty: Boolean = true): Flow<T> =
    if (value is Flow<*>)
        value as Flow<T>
    else if (value == null && nullToEmpty)
        emptyFlow()
    else
        flowOf(value as T)
