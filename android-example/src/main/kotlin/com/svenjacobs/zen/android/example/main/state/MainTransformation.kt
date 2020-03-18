package com.svenjacobs.zen.android.example.main.state

import android.util.Log
import com.svenjacobs.zen.android.example.api.Repository
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.*
import com.svenjacobs.zen.common.coroutines.flow.boxInFlow
import com.svenjacobs.zen.core.state.DefaultStateAccessor
import com.svenjacobs.zen.core.state.StateAccessor
import com.svenjacobs.zen.core.state.Transformer
import com.svenjacobs.zen.core.state.withDefault
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class MainTransformation(
    private val repository: Repository,
    private val ioCoroutineContext: CoroutineContext
) : Transformer.Transformation<MainAction, MainState> {

    override suspend fun invoke(
        action: MainAction,
        state: StateAccessor<MainState>
    ): Flow<MainState> {
        val defaultState = state.withDefault { MainState() }
        return boxInFlow(
            when (action) {
                is LoadAction -> loadAction(defaultState)
                is LoadUserPostsAction -> loadUserPostsAction(action, defaultState)
                is ItemClickAction -> itemClickAction(action, defaultState)
                is DialogResponseAction -> dialogResponseAction(action, defaultState)
            }
        )
    }

    private suspend fun loadAction(state: DefaultStateAccessor<MainState>) =
        loadPosts(
            repository.posts(),
            state
        )

    private suspend fun loadUserPostsAction(
        action: LoadUserPostsAction,
        state: DefaultStateAccessor<MainState>
    ) =
        loadPosts(
            repository.postsByUser(action.userId),
            state
        )

    private fun itemClickAction(
        action: ItemClickAction,
        state: DefaultStateAccessor<MainState>
    ): Flow<MainState> =
        flow {
            emit(state.value.copy(dialogTitle = action.item.title))
            emit(state.value.copy(dialogTitle = null))
        }

    private fun dialogResponseAction(
        action: DialogResponseAction,
        state: DefaultStateAccessor<MainState>
    ) =
        flow {
            emit(state.value.copy(toastMessage = if (action.success) "YES was clicked" else "NO was clicked"))
            emit(state.value.copy(toastMessage = null))
        }

    private suspend fun loadPosts(
        flow: Flow<List<JsonPost>>,
        state: DefaultStateAccessor<MainState>
    ) =
        flow
            .flowOn(ioCoroutineContext)
            .map {
                state.value.copy(
                    isLoading = false,
                    posts = it
                )
            }
            .catch { e ->
                Log.e(TAG, "Error while loading posts", e)

                emit(
                    state.value.copy(
                        isLoading = false,
                        isError = true
                    )
                )
            }
            .onStart {
                emit(state.value.copy(isLoading = true))
            }

    private companion object {
        private const val TAG = "MainTransformation"
    }
}
