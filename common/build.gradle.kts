plugins {
    `kotlin-jvm-module`
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(Dependencies.kotlinxCoroutinesCore)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

artifacts {
    archives(sourcesJar)
}
