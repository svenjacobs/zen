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
    api(project(":zen-core"))
    api(Dependencies.androidXFragmentKtx)
    api(Dependencies.androidXLifecycleLiveDataKtx)
    api(Dependencies.androidXLifecycleViewModelKtx)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

artifacts {
    archives(sourcesJar)
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
