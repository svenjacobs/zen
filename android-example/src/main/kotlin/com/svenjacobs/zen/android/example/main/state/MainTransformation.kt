package com.svenjacobs.zen.android.example.main.state

import com.svenjacobs.zen.android.example.api.Repository
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
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
            }
        )
    }

    private suspend fun loadAction(
        currentState: MainState
    ) =
        repository.posts()
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
