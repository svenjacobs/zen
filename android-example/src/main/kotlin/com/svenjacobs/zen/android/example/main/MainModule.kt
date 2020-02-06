@file:Suppress("FunctionName")

package com.svenjacobs.zen.android.example.main

import com.svenjacobs.zen.android.example.inject.Names.COROUTINE_CONTEXT_IO
import com.svenjacobs.zen.android.example.inject.ZenFragmentCoroutineScopeModule
import com.svenjacobs.zen.android.example.inject.ZenFragmentViewModelStateModule
import com.svenjacobs.zen.android.example.main.master.MainZenMasterContract
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.android.example.main.state.MainTransformation
import com.svenjacobs.zen.android.example.main.state.MainViewModel
import com.svenjacobs.zen.android.example.main.view.MainFragment
import com.svenjacobs.zen.android.example.main.view.MainView
import com.svenjacobs.zen.di.support.katana.ZenModule
import org.rewedigital.katana.Module
import org.rewedigital.katana.androidx.viewmodel.viewModel
import org.rewedigital.katana.dsl.get

fun MainModule(
    fragment: MainFragment
) = Module(
    name = "MainModule",
    includes = listOf(
        ZenFragmentCoroutineScopeModule(fragment),
        ZenFragmentViewModelStateModule<MainState, MainViewModel>(fragment),
        ZenModule(
            view = { fragment as MainView },
            transformation = {
                MainTransformation(
                    get(),
                    get(name = COROUTINE_CONTEXT_IO)
                )
            },
            contract = { MainZenMasterContract() }
        )
    )
) {

    viewModel { MainViewModel() }
}
