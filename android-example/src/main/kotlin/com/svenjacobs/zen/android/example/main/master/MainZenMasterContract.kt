package com.svenjacobs.zen.android.example.main.master

import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadUserPostsAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.android.example.main.view.MainView
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.state.sideEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.plus

class MainZenMasterContract : ZenMaster.Contract<MainView, MainAction, MainState> {

    @Suppress("RedundantWith")
    override fun CoroutineScope.onViewReady(view: MainView) {
        with(this + SupervisorJob(coroutineContext[Job])) {
            // Here for instance click events could be handled that do not modify the state but
            // navigate to another feature / Fragment.
        }
    }

    override fun actions(view: MainView) =
        merge(
            view.onCreateEvents.map { LoadAction },
            view.onFloatingActionButtonClicks.map { LoadUserPostsAction(userId = 1) }
        )

    override fun stateChanges(state: Flow<MainState>, view: MainView) =
        merge(
            state.sideEffect(
                select = { isLoading },
                onEach = { view.setLoadingStateVisible(value) }
            ),
            state.sideEffect(
                select = { isError },
                onEach = { view.setErrorStateVisible(value) }
            ),
            state.sideEffect(
                select = { posts },
                onEach = { view.renderPosts(value) }
            )
        )
}
