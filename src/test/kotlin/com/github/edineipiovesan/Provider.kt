package com.github.edineipiovesan

import com.github.edineipiovesan.Profile.DEFAULT
import com.github.edineipiovesan.Profile.QA
import com.github.edineipiovesan.chain.ValidationProfile
import java.io.File
import java.util.*

enum class Profile { DEFAULT, QA, PRD }

fun propertiesYamlSet() = Profile.values().map { properties("yaml", it) }.toSet()
fun propertiesYmlSet() = Profile.values().map { properties("yml", it) }.toSet()
fun propertiesSet() = Profile.values().map { properties("properties", it) }.toSet()

fun propertiesFileYamlSet() = Profile.values().map { file(it, "yaml") }.toSet()
fun propertiesFileYmlSet() = Profile.values().map { file(it, "yml") }.toSet()
fun propertiesFileSet() = Profile.values().map { file(it, "properties") }.toSet()

fun analysisProfileYamlSet() = Profile.values().map { validationProfile(it, "yaml") }.toSet()
fun analysisProfileYmlSet() = Profile.values().map { validationProfile(it, "yml") }.toSet()
fun analysisProfileSet() = Profile.values().map { validationProfile(it, "properties") }.toSet()

private fun file(profile: Profile, extension: String): File {
    val filename = if (profile == DEFAULT) "application.$extension"
    else "application-${profile.name.toLowerCase()}.$extension"

    return File("src/test/resources/$extension/$filename")
}

private fun validationProfile(profile: Profile, extension: String) = ValidationProfile(
    name = profile.name.toLowerCase(),
    file = file(profile, extension),
    directoryPath = "src/test/resources/$extension"
)

private fun commonsProperties(): Properties {
    val properties = Properties()

    properties["plugin.author.name"] = "Edinei Piovesan"
    properties["plugin.author.github"] = "https://github.com/edineipiovesan"
    properties["plugin.display-name"] = "Spring Profile Config Checker"
    properties["plugin.name"] = "spring-profile-config-checker"
    properties["plugin.version"] = "1.0.0-SNAPSHOT"

    return properties
}

fun properties(fileType: String = "yaml", profile: Profile = DEFAULT): Properties {
    val properties = commonsProperties()
    properties["parser"] = fileType

    if (setOf(DEFAULT, QA).contains(profile))
        properties["plugin.default-value"] = "this-is-${profile.name.toLowerCase()}-value"

    return properties
}

fun validateProperties(profile: Profile = DEFAULT, vararg props: String): Properties {
    val properties = properties(profile = profile)
    val filteredProperties = Properties()
    properties.filter { entry -> props.contains(entry.key) }
        .map { filteredProperties.put(it.key, it.value) }

    return filteredProperties
}
