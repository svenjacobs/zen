@file:Suppress("FunctionName")

package com.svenjacobs.zen.android.example.inject

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.svenjacobs.zen.android.state.MutableLiveDataStateMutator
import com.svenjacobs.zen.android.state.StateViewModel
import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateMutator
import com.svenjacobs.zen.di.support.katana.Names.ZEN_STATE_MUTATOR
import org.rewedigital.katana.Module
import org.rewedigital.katana.androidx.viewmodel.viewModel
import org.rewedigital.katana.dsl.factory
import org.rewedigital.katana.dsl.get

/**
 * Provides injections of [LiveData] and [MutableLiveData] instances of type [State].
 * Assumes that a [StateViewModel] is bound in another module.
 */
inline fun <reified S : State, reified VM : StateViewModel<S>> ZenFragmentViewModelStateModule(
    fragment: Fragment
) = Module(
    name = "ZenFragmentViewModelStateModule"
) {

    // MutableLiveData<S>
    factory { viewModel<VM>(fragment).state }

    factory<LiveData<S>> { get<MutableLiveData<S>>() }

    factory<StateMutator<S>>(name = ZEN_STATE_MUTATOR) {
        MutableLiveDataStateMutator(
            get()
        )
    }
}
