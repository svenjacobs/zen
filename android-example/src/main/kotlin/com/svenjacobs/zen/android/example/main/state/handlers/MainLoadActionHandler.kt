package com.svenjacobs.zen.android.example.main.state.handlers

import com.svenjacobs.zen.android.example.api.Repository
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.core.state.DefaultStateAccessor
import kotlin.coroutines.CoroutineContext

class MainLoadActionHandler(
    override val ioCoroutineContext: CoroutineContext,
    private val repository: Repository
) : BasePostsActionHandler<LoadAction> {

    override suspend fun invoke(
        action: LoadAction,
        state: DefaultStateAccessor<MainState>
    ) =
        loadPosts(
            repository.posts(),
            state
        )
}
