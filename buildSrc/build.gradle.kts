plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    google()
    jcenter()
}

dependencies {
    implementation(kotlin("gradle-plugin", version = "1.3.71"))
    implementation("com.android.tools.build:gradle:3.6.2")
}
