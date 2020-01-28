plugins {
    id("com.android.library")
    kotlin("android")
    //`maven-publish`
    //id("digital.wup.android-maven-publish") version "3.6.3"
    id("com.github.dcendents.android-maven")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.2")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")
}

dependencies {
    val kotlinxCoroutines = "1.3.3"
    val androidXLifecycle = "2.2.0"
    val spek = "2.0.9"

    implementation(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutines")
    api("androidx.fragment:fragment-ktx:1.2.0")
    api("androidx.lifecycle:lifecycle-livedata-ktx:$androidXLifecycle")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidXLifecycle")

    testImplementation("org.amshove.kluent:kluent:1.59")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek")
}

//publishing {
//    repositories {
//        maven {
//            val github = Properties()
//            rootProject.file("github.properties").inputStream().use { github.load(it) }
//
//            name = "GitHubPackages"
//            url = uri("https://maven.pkg.github.com/svenjacobs/zen")
//            credentials {
//                username = github["gpr.usr"] as String? ?: System.getenv("GPR_USER")
//                password = github["gpr.key"] as String? ?: System.getenv("GPR_API_KEY")
//            }
//        }
//    }
//    publications {
//        create<MavenPublication>("gpr") {
//            artifactId = "zen"
//            from(components["android"])
//        }
//    }
//}
