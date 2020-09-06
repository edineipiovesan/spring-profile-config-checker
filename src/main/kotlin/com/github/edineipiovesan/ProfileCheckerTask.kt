package com.github.edineipiovesan

import com.github.edineipiovesan.utils.filterProfiles
import com.github.edineipiovesan.utils.filterProperties
import com.github.edineipiovesan.utils.getFiles
import com.github.edineipiovesan.utils.getProfile
import com.github.edineipiovesan.utils.getPropertyParser
import com.github.edineipiovesan.validator.isPropertyValid
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


open class ProfileCheckerTask : DefaultTask() {

    @TaskAction
    fun action() {
        val extension = project.extensions.run {
            findByName("profileChecker") as ProfileCheckerExtension
        }

        println("=-=-= Spring Profile Config Checker")
        val isAllPropertiesValid = validateProfileConfig(extension)
        println("=-=-= Done!")

        if (isAllPropertiesValid.contains(false))
            throw IllegalStateException("Not all properties has different values. Check your properties profiles.")
    }

    private fun validateProfileConfig(extension: ProfileCheckerExtension): Set<Boolean> {
        val propertiesValidation = hashSetOf<Boolean>()
        val directories = extension.dirs

        directories.forEach { propertyDir ->
            println("=-=-= Scanning directory $propertyDir")
            val propertyFiles = getFiles(propertyDir)
                .also { println("=-=-= Property files: ${it.map { file -> file.name }}") }

            val filteredProfiles = filterProfiles(propertyFiles, extension.ignoreProfiles)
                .also {
                    println(
                        """
    |=-=-= Ignored profiles: ${extension.ignoreProfiles.joinToString(separator = ", ")}
    |=-=-= Remaining profiles: ${it.joinToString(separator = ", ") { file -> getProfile(file) }}""".trimMargin()
                    )
                }

            val propertyParser = getPropertyParser(filteredProfiles)
            val properties = filteredProfiles.map { propertyParser.parse(it) }
            val filteredProperties = properties.map { it.filterProperties(extension.validate) }

            println("=-=-= Validating")
            propertiesValidation.addAll(extension.validate.map { property ->
                isPropertyValid(filteredProperties, property)
                    .also { if (!it) println("=-xxx-= Invalid property: $property") }
            }.toSet())
        }

        return propertiesValidation
    }

}