package com.svenjacobs.zen.android.example.inject

import com.svenjacobs.zen.android.example.inject.Names.COROUTINE_CONTEXT_IO
import kotlinx.coroutines.Dispatchers
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.singleton

val CoroutineModule = Module(
    name = "CoroutineModule"
) {

    singleton(name = COROUTINE_CONTEXT_IO) { Dispatchers.IO }
}
