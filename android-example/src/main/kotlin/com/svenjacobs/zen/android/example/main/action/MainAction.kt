package com.svenjacobs.zen.android.example.main.action

import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.core.action.Action

sealed class MainAction : Action {

    object LoadAction : MainAction()

    data class LoadUserPostsAction(
        val userId: Int
    ) : MainAction()

    data class ItemClickAction(
        val item: JsonPost
    ) : MainAction()

    data class DialogResponseAction(
        val success: Boolean
    ) : MainAction()
}
