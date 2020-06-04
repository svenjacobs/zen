@file:Suppress("FunctionName")

package com.svenjacobs.zen.android.example.main

import android.util.Log
import com.svenjacobs.zen.android.example.inject.Names.COROUTINE_CONTEXT_IO
import com.svenjacobs.zen.android.example.inject.ZenFragmentViewModelStateModule
import com.svenjacobs.zen.android.example.main.master.MainZenMasterContract
import com.svenjacobs.zen.android.example.main.state.MainState
import com.svenjacobs.zen.android.example.main.state.MainTransformation
import com.svenjacobs.zen.android.example.main.state.MainViewModel
import com.svenjacobs.zen.android.example.main.state.handlers.MainDialogResponseActionHandler
import com.svenjacobs.zen.android.example.main.state.handlers.MainItemClickActionHandler
import com.svenjacobs.zen.android.example.main.state.handlers.MainLoadActionHandler
import com.svenjacobs.zen.android.example.main.state.handlers.MainLoadUserPostsActionHandler
import com.svenjacobs.zen.android.example.main.view.MainFragment
import com.svenjacobs.zen.android.master.AndroidLoggingMiddleware
import com.svenjacobs.zen.di.support.katana.ZenModule
import org.rewedigital.katana.Module
import org.rewedigital.katana.androidx.viewmodel.viewModel
import org.rewedigital.katana.dsl.factory
import org.rewedigital.katana.dsl.get

fun MainModule(
    fragment: MainFragment
) = Module(
    name = "MainModule",
    includes = listOf(
        ZenFragmentViewModelStateModule<MainState, MainViewModel>(fragment),
        ZenModule(
            view = { fragment },
            transformation = {
                MainTransformation(
                    get(),
                    get(),
                    get(),
                    get()
                )
            },
            contract = { MainZenMasterContract() },
            middleware = { AndroidLoggingMiddleware() },
            exceptionHandler = { { e -> Log.e("MainModule", "Unhandled exception", e) } }
        )
    )
) {

    viewModel { MainViewModel() }

    factory { MainLoadActionHandler(get(name = COROUTINE_CONTEXT_IO), get()) }

    factory { MainLoadUserPostsActionHandler(get(name = COROUTINE_CONTEXT_IO), get()) }

    factory { MainItemClickActionHandler() }

    factory { MainDialogResponseActionHandler() }
}
