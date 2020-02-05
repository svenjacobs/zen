package com.svenjacobs.zen.android.example.api

import com.svenjacobs.zen.android.example.inject.HttpModule
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.factory
import org.rewedigital.katana.dsl.get

val ApiModule = Module(
    name = "ApiModule",
    includes = listOf(HttpModule)
) {

    factory { Repository(get()) }
}
