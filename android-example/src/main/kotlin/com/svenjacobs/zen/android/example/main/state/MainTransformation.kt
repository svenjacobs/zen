package com.svenjacobs.zen.android.example.main.state

import android.util.Log
import com.svenjacobs.zen.android.example.api.Repository
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.*
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
                is ItemClickAction -> itemClickAction(action, state)
                is DialogResponseAction -> dialogResponseAction(action, state)
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

    private fun itemClickAction(
        action: ItemClickAction,
        currentState: MainState
    ) =
        flowOf(
            currentState.copy(
                dialogTitle = action.item.title
            ),
            currentState.copy(
                dialogTitle = null
            )
        )

    // TODO: Do we rather need StateAccessor here?
    private fun dialogResponseAction(
        action: DialogResponseAction,
        currentState: MainState
    ) =
        flowOf(
            currentState.copy(
                dialogTitle = null,
                toastMessage = if (action.success) "YES was clicked" else "NO was clicked"
            ),
            currentState.copy(
                dialogTitle = null,
                toastMessage = null
            )
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
            .catch { e ->
                Log.e(TAG, "Error while loading posts", e)

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

    private companion object {
        private const val TAG = "MainTransformation"
    }
}
