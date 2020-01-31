plugins {
    `kotlin-jvm-module`
}

dependencies {
    api(project(":zen-common"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

artifacts {
    archives(sourcesJar)
}
