@file:Suppress("FunctionName")

package com.svenjacobs.zen.common

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Returns [Pair] if all values are non-null, else returns `null`.
 */
@ExperimentalContracts
fun <A, B> SafePair(first: A?, second: B?): Pair<A, B>? {
    contract {
        returnsNotNull() implies (first != null && second != null)
        returns(null) implies (first == null || second == null)
    }
    return if (first != null && second != null) Pair(first, second) else null
}

/**
 * Returns [Triple] if all values are non-null, else returns `null`.
 */
@ExperimentalContracts
fun <A, B, C> SafeTriple(first: A?, second: B?, third: C?): Triple<A, B, C>? {
    contract {
        returnsNotNull() implies (first != null && second != null && third != null)
        returns(null) implies (first == null || second == null || third == null)
    }
    return if (first != null && second != null && third != null) Triple(first, second, third) else null
}
