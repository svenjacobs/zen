import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.6.0.0")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.61")
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.28.0"
}

subprojects {
    if (name != "android-example") {
        apply(plugin = "maven")
    }

    repositories {
        google()
        jcenter()
    }

    group = "com.svenjacobs.zen"
    version = "0.8"

    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlin.Experimental",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
            )
        }
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {

    fun isNonStable(version: String) =
        listOf("alpha", "beta", "rc", "eap").any { version.toLowerCase().contains(it) }

    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
