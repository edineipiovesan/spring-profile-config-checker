package com.github.edineipiovesan

import com.github.edineipiovesan.chain.ValidationProfile
import java.io.File
import java.util.*

fun propertiesYamlSet() =
    setOf(yamlProperties(), yamlProperties(), yamlProperties()) // FIXME: 07/09/20 profile name inside properties

fun propertiesYmlSet() =
    setOf(ymlProperties(), ymlProperties(), ymlProperties()) // FIXME: 07/09/20 profile name inside properties

fun propertiesSet() = setOf(properties(), properties(), properties()) // FIXME: 07/09/20 profile name inside properties

fun propertiesFileYamlSet() = setOf(
    file("default", "yaml"),
    file("qa", "yaml"),
    file("prd", "yaml")
)

fun propertiesFileYmlSet() = setOf(
    file("default", "yml"),
    file("qa", "yml"),
    file("prd", "yml")
)

fun propertiesFilePropertiesSet() = setOf(
    file("default", "properties"),
    file("qa", "properties"),
    file("prd", "properties")
)

fun analysisProfileYamlSet() = setOf(
    validationProfile("default", "yaml"),
    validationProfile("qa", "yaml"),
    validationProfile("prd", "yaml")
)

fun analysisProfileYmlSet() = setOf(
    validationProfile("default", "yml"),
    validationProfile("qa", "yml"),
    validationProfile("prd", "yml")
)

fun analysisProfilePropertiesSet() = setOf(
    validationProfile("default", "properties"),
    validationProfile("qa", "properties"),
    validationProfile("prd", "properties")
)

private fun file(profile: String, extension: String): File {
    val filename = if (profile.equals("default", ignoreCase = true)) "application.$extension"
    else "application-$profile.$extension"

    return File("src/test/resources/$extension/$filename")
}

private fun validationProfile(profile: String, extension: String) = ValidationProfile(
    name = profile,
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

private fun ymlProperties(): Properties {
    val properties = commonsProperties()
    properties["parser"] = "yml"
    return properties
}

private fun yamlProperties(): Properties {
    val properties = commonsProperties()
    properties["parser"] = "yaml"
    return properties
}

private fun properties(): Properties {
    val properties = commonsProperties()
    properties["parser"] = "properties"
    return properties
}