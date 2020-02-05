package com.svenjacobs.zen.android.example.inject

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.singleton

val HttpModule = Module(
    name = "HttpModule"
) {
    singleton {
        HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}
