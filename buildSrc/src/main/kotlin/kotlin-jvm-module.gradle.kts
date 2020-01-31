plugins {
    kotlin("jvm")
}

dependencies {
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
