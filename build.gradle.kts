import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.5.2.0")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

subprojects {
    apply(plugin = "maven")

    repositories {
        google()
        jcenter()
    }

    group = "com.svenjacobs.zen"
    version = "0.2"

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
