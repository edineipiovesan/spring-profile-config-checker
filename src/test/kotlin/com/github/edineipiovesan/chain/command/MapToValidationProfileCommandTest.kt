package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.ProfileCheckerExtension
import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.propertiesFileYamlSet
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test

class MapToValidationProfileCommandTest {
    private val command = MapToValidationProfileCommand()

    @Test
    fun `map files to ValidationProfile object`() {
        val path = "src/test/resources/yaml"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension).copy(propertiesFiles = hashMapOf(path to propertiesFileYamlSet()))

        val newContext = command.execute(context)

        newContext.analysisProfile shouldHaveSize 3
        newContext.analysisProfile.map { it.name } shouldContainExactlyInAnyOrder setOf("default", "qa", "prd")
        newContext.analysisProfile.map { it.directoryPath }.toSet() shouldContainExactlyInAnyOrder setOf(path)
    }
}