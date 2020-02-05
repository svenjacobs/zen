package com.svenjacobs.zen.android.example.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle.Event
import com.svenjacobs.zen.android.example.R
import com.svenjacobs.zen.android.example.ZenApp
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.main.MainModule
import com.svenjacobs.zen.android.view.ZenFragment
import com.svenjacobs.zen.core.master.ZenMaster
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.rewedigital.katana.Component
import org.rewedigital.katana.KatanaTrait
import org.rewedigital.katana.inject
import reactivecircus.flowbinding.lifecycle.events

class MainFragment : ZenFragment(),
    KatanaTrait,
    MainView {

    override val component =
        Component.Builder()
            .addDependsOn(ZenApp.applicationComponent)
            .addModule(MainModule(this))
            .build()

    override val zenMaster: ZenMaster by inject()

    override val onCreateEvents: Flow<Unit>
        get() = lifecycle.events()
            .filter { it == Event.ON_CREATE }
            .map { Unit }

    private val adapter
        get() = main_posts.adapter as MainPostsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onBeforeZenMasterViewReady(view: View, savedInstanceState: Bundle?) {
        super.onBeforeZenMasterViewReady(view, savedInstanceState)

        main_posts.adapter = MainPostsAdapter()
    }

    override fun setLoadingStateVisible(visible: Boolean) {
        if (visible) {
            main_progress_bar.show()
        } else {
            main_progress_bar.hide()
        }
        main_posts.isGone = visible
    }

    override fun setErrorStateVisible(visible: Boolean) {
        main_error_group.isVisible = visible
        main_posts.isGone = visible
    }

    override fun renderPosts(posts: List<JsonPost>) {
        adapter.posts = posts
    }
}
