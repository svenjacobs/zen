package com.svenjacobs.zen.android.example.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle.Event
import com.svenjacobs.zen.android.example.ZenApp
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.databinding.FragmentMainBinding
import com.svenjacobs.zen.android.example.main.MainModule
import com.svenjacobs.zen.android.example.main.view.MainView.DialogResponse
import com.svenjacobs.zen.android.view.ZenFragment
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.util.EventBroadcaster
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.rewedigital.katana.Component
import org.rewedigital.katana.KatanaTrait
import org.rewedigital.katana.inject
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.lifecycle.events

class MainFragment : ZenFragment(),
    KatanaTrait,
    MainView {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override val component =
        Component.Builder()
            .addDependsOn(ZenApp.applicationComponent)
            .addModule(MainModule(this))
            .build()

    override val zenMaster: ZenMaster by inject()

    override val viewLifecycleEvents: Flow<Event>
        get() = viewLifecycleOwner.lifecycle.events()

    override val onFloatingActionButtonClicks: Flow<Unit>
        get() = binding.mainButton.clicks()

    override val onItemClicks: Flow<JsonPost>
        get() = callbackFlow {
            adapter.onItemClickListener = { offer(it) }
            awaitClose { adapter.onItemClickListener = null }
        }

    override val dialogResponse: Flow<DialogResponse>
        get() = broadcaster.asFlow()

    private val adapter
        get() = binding.mainPosts.adapter as MainPostsAdapter

    private val broadcaster = EventBroadcaster()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBeforeZenMasterViewReady(view: View, savedInstanceState: Bundle?) {
        super.onBeforeZenMasterViewReady(view, savedInstanceState)

        binding.mainPosts.adapter = MainPostsAdapter()
    }

    override fun setLoadingStateVisible(visible: Boolean) {
        if (visible) {
            binding.mainProgressBar.show()
        } else {
            binding.mainProgressBar.hide()
        }
        binding.mainPosts.isGone = visible
    }

    override fun setErrorStateVisible(visible: Boolean) {
        binding.mainErrorGroup.isVisible = visible
        binding.mainPosts.isGone = visible
    }

    override fun renderPosts(posts: List<JsonPost>) {
        adapter.posts = posts
    }

    override fun showDialog(title: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setPositiveButton(android.R.string.yes) { _, _ -> broadcaster.broadcast(DialogResponse.YES) }
            .setNegativeButton(android.R.string.no) { _, _ -> broadcaster.broadcast(DialogResponse.NO) }
            .show()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
