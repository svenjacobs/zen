package com.svenjacobs.zen.android.example.main.state

import com.svenjacobs.zen.android.example.api.Repository
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadUserPostsAction
import com.svenjacobs.zen.common.coroutines.flow.boxInFlow
import com.svenjacobs.zen.core.state.Transformer
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class MainTransformation(
    private val repository: Repository,
    private val ioCoroutineContext: CoroutineContext
) : Transformer.Transformation<MainAction, MainState> {

    override suspend fun invoke(action: MainAction, currentState: MainState?): Flow<MainState> {
        val state = currentState ?: MainState()
        return boxInFlow(
            when (action) {
                is LoadAction -> loadAction(state)
                is LoadUserPostsAction -> loadUserPostsAction(action, state)
            }
        )
    }

    private suspend fun loadAction(
        currentState: MainState
    ) =
        loadPosts(
            repository.posts(),
            currentState
        )

    private suspend fun loadUserPostsAction(
        action: LoadUserPostsAction,
        currentState: MainState
    ) =
        loadPosts(
            repository.postsByUser(action.userId),
            currentState
        )

    private suspend fun loadPosts(
        flow: Flow<List<JsonPost>>,
        currentState: MainState
    ) =
        flow
            .flowOn(ioCoroutineContext)
            .map {
                currentState.copy(
                    isLoading = false,
                    posts = it
                )
            }
            .catch {
                emit(
                    currentState.copy(
                        isLoading = false,
                        isError = true
                    )
                )
            }
            .onStart {
                emit(currentState.copy(isLoading = true))
            }
}
