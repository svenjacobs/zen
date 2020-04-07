package com.svenjacobs.zen.android.example.inject

import com.svenjacobs.zen.android.example.inject.Names.*
import kotlinx.coroutines.Dispatchers
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.singleton

val CoroutineModule = Module(
    name = "CoroutineModule"
) {

    singleton(name = COROUTINE_CONTEXT_DEFAULT) { Dispatchers.Default }

    singleton(name = COROUTINE_CONTEXT_IO) { Dispatchers.IO }

    singleton(name = COROUTINE_CONTEXT_MAIN) { Dispatchers.Main.immediate }
}
