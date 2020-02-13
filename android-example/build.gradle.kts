plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)

        applicationId = "com.svenjacobs.zen.android.example"
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    packagingOptions {
        exclude("META-INF/kotlinx-coroutines-core.kotlin_module")
        exclude("META-INF/kotlinx-serialization-runtime.kotlin_module")
    }
}

dependencies {
    implementation(project(":zen-android"))
    implementation(project(":zen-di-support-katana"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("org.rewedigital.katana:katana-android:1.12.1")
    implementation("org.rewedigital.katana:katana-androidx-viewmodel:1.12.1")
    implementation("io.ktor:ktor-client-android:1.3.1")
    implementation("io.ktor:ktor-client-serialization-jvm:1.3.1")
    implementation("io.github.reactivecircus.flowbinding:flowbinding-lifecycle:0.9.0")
}
