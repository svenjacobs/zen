package com.svenjacobs.zen.android.example.main.view

import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.core.view.ZenView
import kotlinx.coroutines.flow.Flow

interface MainView : ZenView {

    val onCreateEvents: Flow<Unit>

    fun setLoadingStateVisible(visible: Boolean)

    fun setErrorStateVisible(visible: Boolean)

    fun renderPosts(posts: List<JsonPost>)
}
