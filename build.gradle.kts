import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.4.0"
    id("com.gradle.plugin-publish") version "0.12.0"
    id("java-gradle-plugin")
    id("maven-publish")
}
group = "com.github.edineipiovesan"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}
dependencies {
    gradleApi()
    gradleTestKit()
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.2")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0-RC1")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
tasks.test {
    useJUnitPlatform()
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
pluginBundle {
    website = "http://github.com/edineipiovesan/"
    vcsUrl = "http://github.com/edineipiovesan/spring-profile-config-checker"
    description = "Check if properties has different values for different profiles."

    (plugins) {

        "spring-profile-config-checker" {
            // id is captured from java-gradle-plugin configuration
            displayName = "Spring Profile Config Checker"
            tags = listOf("property", "spring", "profile", "environment")
            version = "0.1"
        }
    }
}