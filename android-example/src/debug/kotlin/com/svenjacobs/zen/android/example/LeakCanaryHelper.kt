package com.svenjacobs.zen.android.example

import leakcanary.LeakCanary

object LeakCanaryHelper {

    fun configure() {
        LeakCanary.config = LeakCanary.config.copy(
            retainedVisibleThreshold = 1
        )
    }
}
