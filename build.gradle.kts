import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.5.2.0")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.61")
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.27.0"
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
    version = "0.4"

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
