package com.svenjacobs.zen.core.state

import com.svenjacobs.zen.core.action.Action
import kotlinx.coroutines.flow.Flow

/**
 * Allows separating complex transformations of certain [Actions][Action] into own handlers.
 */
interface TransformationActionHandler<in A : Action, S : State> {

    suspend operator fun invoke(
        action: A,
        state: DefaultStateAccessor<S>
    ): Flow<S>
}
