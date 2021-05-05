plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin", version = "1.5.0"))
    implementation("com.android.tools.build:gradle:4.2.0")
}
