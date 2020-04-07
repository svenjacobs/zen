package com.svenjacobs.zen.android.example.main.state.handlers

import com.svenjacobs.zen.android.example.main.action.MainAction.DialogResponseAction
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.core.state.DefaultStateAccessor
import com.svenjacobs.zen.core.state.TransformationActionHandler
import kotlinx.coroutines.flow.flow

class MainDialogResponseActionHandler :
    TransformationActionHandler<DialogResponseAction, MainState> {

    override suspend fun invoke(
        action: DialogResponseAction,
        state: DefaultStateAccessor<MainState>
    ) =
        flow {
            emit(
                state
                    .get()
                    .copy(toastMessage = if (action.success) "YES was clicked" else "NO was clicked")
            )
            emit(state.get().copy(toastMessage = null))
        }
}
