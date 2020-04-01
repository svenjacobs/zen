package com.svenjacobs.zen.android.example.main.state.handlers

import android.util.Log
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.core.state.DefaultStateAccessor
import com.svenjacobs.zen.core.state.TransformationActionHandler
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

internal interface BasePostsActionHandler<A : MainAction> :
    TransformationActionHandler<A, MainState> {

    val ioCoroutineContext: CoroutineContext

    suspend fun loadPosts(
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
        private const val TAG = "BasePostsActionHandler"
    }
}
