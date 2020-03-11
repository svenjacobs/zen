package com.svenjacobs.zen.android.example.main.master

import android.util.Log
import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadUserPostsAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.android.example.main.view.MainView
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.state.sideEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class MainZenMasterContract : ZenMaster.Contract<MainView, MainAction, MainState> {

    @Suppress("RedundantWith")
    override fun CoroutineScope.onViewReady(view: MainView) {
        // Here for instance click events could be handled that do not modify the state but
        // navigate to another feature / Fragment.

        // Example of handling lifecycle events for tracking for example
        view.viewLifecycleEvents
            .onEach { Log.d(TAG, "View lifecycle event: ${it.name}") }
            .launchIn(this)
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

    private companion object {
        private const val TAG = "MainZenMasterContract"
    }
}
