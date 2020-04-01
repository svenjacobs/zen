package com.svenjacobs.zen.android.example.main.state.handlers

import com.svenjacobs.zen.android.example.api.Repository
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadUserPostsAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.core.state.DefaultStateAccessor
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class MainLoadUserPostsActionHandler(
    override val ioCoroutineContext: CoroutineContext,
    private val repository: Repository
) : BasePostsActionHandler<LoadUserPostsAction> {

    override suspend fun invoke(
        action: LoadUserPostsAction,
        state: DefaultStateAccessor<MainState>
    ) =
        loadPosts(
            repository.postsByUser(action.userId),
            state
        )
}
