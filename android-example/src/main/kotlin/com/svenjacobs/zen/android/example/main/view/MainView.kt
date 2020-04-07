package com.svenjacobs.zen.android.example.main.view

import androidx.lifecycle.Lifecycle
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.core.view.ZenView
import kotlinx.coroutines.flow.Flow

interface MainView : ZenView {

    enum class DialogResponse { YES, NO }

    val viewLifecycleEvents: Flow<Lifecycle.Event>

    val onFloatingActionButtonClicks: Flow<Unit>

    val onItemClicks: Flow<JsonPost>

    val dialogResponse: Flow<DialogResponse>

    fun setLoadingStateVisible(visible: Boolean)

    fun setErrorStateVisible(visible: Boolean)

    fun renderPosts(posts: List<JsonPost>)

    fun showDialog(title: String)

    fun showToast(message: String)

    fun setTest(text: String)
}
