package com.svenjacobs.zen.android.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.svenjacobs.zen.core.master.ZenMaster

/**
 * Base class for [Fragments][Fragment] that use a [ZenMaster].
 *
 * @see onViewCreated
 * @see onBeforeZenMasterViewReady
 */
abstract class ZenFragment : Fragment() {

    protected abstract val zenMaster: ZenMaster

    /**
     * Is called in [onViewCreated] and before [ZenMaster.onViewReady].
     * Should be used to perform final initialization of view.
     */
    protected open fun onBeforeZenMasterViewReady(view: View, savedInstanceState: Bundle?) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBeforeZenMasterViewReady(view, savedInstanceState)

        zenMaster.onViewReady(viewLifecycleOwner.lifecycleScope)
    }
}
