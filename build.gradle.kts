import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
import org.gradle.api.logging.LogLevel.LIFECYCLE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktlint by configurations.creating

plugins {
    java
    application
    jacoco

    kotlin("jvm") version "1.4.0"

    /**
     * FIXME: 07/09/20
     * Building warning caused by java-gradle-plugin dependency and
     * gradle version. Details can be found at issue KT-38010.
     * https://youtrack.jetbrains.com/issue/KT-38010
     */
    id("java-gradle-plugin")
    id("maven-publish")

    id("org.sonarqube") version "3.0"
    id("com.adarshr.test-logger") version "2.1.0"
    id("org.barfuin.gradle.jacocolog") version "1.2.2"
}

group = "com.github.edineipiovesan"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    gradleApi()
    gradleTestKit()

    ktlint("com.pinterest:ktlint:0.38.1")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0-RC1")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("io.kotest:kotest-assertions-core:4.2.3")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}

tasks.test {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.sonarqube {
    dependsOn(tasks.jacocoTestReport)
}

testlogger {
    theme = MOCHA
    showExceptions = true
    showStackTraces = true
    showFullStackTraces = false
    showCauses = true
    slowThreshold = 2000
    showSummary = true
    showSimpleNames = true
    showPassed = true
    showSkipped = true
    showFailed = true
    showStandardStreams = false
    showPassedStandardStreams = true
    showSkippedStandardStreams = true
    showFailedStandardStreams = true
    logLevel = LIFECYCLE
}

application {
    mainClassName = "MainKt"
}

gradlePlugin {
    plugins {
        create("spring-profile-config-checker") {
            id = "com.github.edineipiovesan.spring-profile-config-checker"
            implementationClass = "com.github.edineipiovesan.SpringProfileConfigChecker"
        }
    }
}