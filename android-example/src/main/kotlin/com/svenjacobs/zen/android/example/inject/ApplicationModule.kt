package com.svenjacobs.zen.android.example.inject

import com.svenjacobs.zen.android.example.api.ApiModule
import org.rewedigital.katana.Module

val ApplicationModule = Module(
    name = "ApplicationModule",
    includes = listOf(
        ApiModule,
        CoroutineModule,
        ZenCoroutineModule
    )
)
