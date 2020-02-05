package com.svenjacobs.zen.android.example.inject

import com.svenjacobs.zen.di.support.katana.Names.ZEN_COROUTINE_CONTEXT_TRANSFORMATION
import com.svenjacobs.zen.di.support.katana.Names.ZEN_COROUTINE_CONTEXT_UI
import kotlinx.coroutines.Dispatchers
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.singleton

val ZenCoroutineModule = Module(
    name = "ZenCoroutineModule"
) {

    singleton(name = ZEN_COROUTINE_CONTEXT_TRANSFORMATION) { Dispatchers.Default }

    singleton(name = ZEN_COROUTINE_CONTEXT_UI) { Dispatchers.Main }
}
