object Versions {
    const val androidXLifecycle = "2.3.1"
    const val katana = "1.14.1"
    const val kotlinxCoroutines = "1.4.3"
    const val spek = "2.0.15"
}

object Dependencies {
    const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val androidXFragmentKtx = "androidx.fragment:fragment-ktx:1.3.3"
    const val androidXLifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidXLifecycle}"
    const val androidXLifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}"
    const val katanaCore = "org.rewedigital.katana:katana-core:${Versions.katana}"

    const val kotlinxCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutines}"
    const val kluent = "org.amshove.kluent:kluent:1.65"
    const val spekDslJvm = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spek}"
    const val spekRunnerJunit5 = "org.spekframework.spek2:spek-runner-junit5:${Versions.spek}"
}
