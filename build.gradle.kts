import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.0")
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.38.0"
}

subprojects {
    if (name != "android-example") {
        apply(plugin = "maven")
    }

    repositories {
        google()
        mavenCentral()
    }

    group = "com.svenjacobs.zen"
    version = "0.30"

    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.Experimental",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview"
            )
        }
    }

    configurations.all {
        resolutionStrategy {
            // Make sure all Kotlin artifacts use the same version
            eachDependency {
                if (requested.group == "org.jetbrains.kotlin") {
                    useVersion("1.5.0")
                }
            }
        }
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {

    fun isNonStable(version: String) =
        listOf("alpha", "beta", "rc", "eap", "-m").any { version.toLowerCase().contains(it) }

    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
