package com.svenjacobs.zen.android.example.inject

import com.svenjacobs.zen.android.example.inject.Names.COROUTINE_CONTEXT_DEFAULT
import com.svenjacobs.zen.android.example.inject.Names.COROUTINE_CONTEXT_MAIN
import com.svenjacobs.zen.di.support.katana.Names.ZEN_COROUTINE_CONTEXT_TRANSFORMATION
import com.svenjacobs.zen.di.support.katana.Names.ZEN_COROUTINE_CONTEXT_UI
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.alias
import kotlin.coroutines.CoroutineContext

val ZenCoroutineModule = Module(
    name = "ZenCoroutineModule"
) {

    alias<CoroutineContext, CoroutineContext>(
        name = ZEN_COROUTINE_CONTEXT_TRANSFORMATION,
        originalName = COROUTINE_CONTEXT_DEFAULT
    )

    alias<CoroutineContext, CoroutineContext>(
        name = ZEN_COROUTINE_CONTEXT_UI,
        originalName = COROUTINE_CONTEXT_MAIN
    )
}
