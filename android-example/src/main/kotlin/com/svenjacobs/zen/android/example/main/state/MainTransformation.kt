package com.svenjacobs.zen.android.example.main.state

import com.svenjacobs.zen.android.example.main.action.MainAction
import com.svenjacobs.zen.android.example.main.action.MainAction.*
import com.svenjacobs.zen.android.example.main.state.handlers.MainDialogResponseActionHandler
import com.svenjacobs.zen.android.example.main.state.handlers.MainItemClickActionHandler
import com.svenjacobs.zen.android.example.main.state.handlers.MainLoadActionHandler
import com.svenjacobs.zen.android.example.main.state.handlers.MainLoadUserPostsActionHandler
import com.svenjacobs.zen.core.state.StateAccessor
import com.svenjacobs.zen.core.state.Transformer
import com.svenjacobs.zen.core.state.withDefault

class MainTransformation(
    private val loadActionHandler: MainLoadActionHandler,
    private val loadUserPostsActionHandler: MainLoadUserPostsActionHandler,
    private val itemClickActionHandler: MainItemClickActionHandler,
    private val dialogResponseActionHandler: MainDialogResponseActionHandler
) : Transformer.Transformation<MainAction, MainState> {

    override suspend fun invoke(
        action: MainAction,
        state: StateAccessor<MainState>
    ) =
        state.withDefault { MainState() }.let { defaultState ->
            when (action) {
                is LoadAction -> loadActionHandler(action, defaultState)
                is LoadUserPostsAction -> loadUserPostsActionHandler(action, defaultState)
                is ItemClickAction -> itemClickActionHandler(action, defaultState)
                is DialogResponseAction -> dialogResponseActionHandler(action, defaultState)
            }
        }
}
