@file:Suppress("FunctionName")

package com.svenjacobs.zen.android.example.inject

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.svenjacobs.zen.di.support.katana.Names.ZEN_COROUTINE_SCOPE_PROVIDER_VIEW_LIFECYCLE
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.factory

/**
 * Provides bindings of [kotlinx.coroutines.CoroutineScope] for Fragment's view lifecycle.
 *
 * Note that scopes are provided via provider functions because instances might change when a
 * Fragment is resumed from a paused state for example.
 */
fun ZenFragmentCoroutineScopeModule(
    fragment: Fragment
) = Module(
    name = "ZenFragmentCoroutineScopeModule"
) {

    factory(name = ZEN_COROUTINE_SCOPE_PROVIDER_VIEW_LIFECYCLE) {
        { fragment.viewLifecycleOwner.lifecycleScope }
    }
}
