package com.svenjacobs.zen.android.example.main.state.handlers

import com.svenjacobs.zen.android.example.main.action.MainAction.ItemClickAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.core.state.DefaultStateAccessor
import com.svenjacobs.zen.core.state.TransformationActionHandler
import kotlinx.coroutines.flow.flow

class MainItemClickActionHandler : TransformationActionHandler<ItemClickAction, MainState> {

    override suspend fun invoke(
        action: ItemClickAction,
        state: DefaultStateAccessor<MainState>
    ) =
        flow {
            emit(state.value.copy(dialogTitle = action.item.title))
            emit(state.value.copy(dialogTitle = null))
        }
}
