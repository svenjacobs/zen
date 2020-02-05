package com.svenjacobs.zen.android.example

import android.app.Application
import com.svenjacobs.zen.android.example.inject.ApplicationModule
import org.rewedigital.katana.Component

class ZenApp : Application() {

    override fun onCreate() {
        super.onCreate()

        applicationComponent = Component(ApplicationModule)
    }

    companion object {
        lateinit var applicationComponent: Component
    }
}
