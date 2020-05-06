package com.svenjacobs.zen.android.example.main.action

import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.core.action.Action

sealed class MainAction : Action {

    enum class Id { Load, LoadUserPosts, ItemClick, DialogResponse }

    object LoadAction : MainAction() {
        override val id = Id.Load
    }

    data class LoadUserPostsAction(
        val userId: Int
    ) : MainAction() {
        override val id = Id.LoadUserPosts
    }

    data class ItemClickAction(
        val item: JsonPost
    ) : MainAction() {
        override val id = Id.ItemClick
    }

    data class DialogResponseAction(
        val success: Boolean
    ) : MainAction() {
        override val id = Id.DialogResponse
    }
}
