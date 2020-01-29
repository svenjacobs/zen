plugins {
    kotlin("jvm")
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(Dependencies.kotlinxCoroutinesCore)

    testImplementation(Dependencies.kluent)
    testImplementation(Dependencies.kotlinxCoroutinesTest)
    testImplementation(Dependencies.spekDslJvm)
    testRuntimeOnly(Dependencies.spekRunnerJunit5)
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

artifacts {
    archives(sourcesJar)
}
