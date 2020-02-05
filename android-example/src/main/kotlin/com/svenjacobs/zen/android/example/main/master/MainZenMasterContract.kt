package com.svenjacobs.zen.android.example.main.master

import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.LoadAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.android.example.main.view.MainView
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.state.sideEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class MainZenMasterContract : ZenMaster.Contract<MainView, MainAction, MainState> {

    override fun actions(view: MainView) =
        view.onCreateEvents.map { LoadAction }

    override suspend fun stateChanges(state: Flow<MainState>, view: MainView) =
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
