package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.*
import com.github.edineipiovesan.chain.ValidationContext
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ParsePropertiesCommandTest {
    private val command = ParsePropertiesCommand()

    @Test
    fun `should parse properties from yaml file`() {
        val path = "src/test/resources/yaml"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileYamlSet()),
            analysisProfile = analysisProfileYamlSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile shouldHaveSize 3
        newContext.analysisProfile.map { it.properties }.toSet() shouldBe propertiesYamlSet()
    }

    @Test
    fun `should parse properties from yml file`() {
        val path = "src/test/resources/yml"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileYmlSet()),
            analysisProfile = analysisProfileYmlSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile shouldHaveSize 3
        newContext.analysisProfile.map { it.properties }.toSet() shouldBe propertiesYmlSet()
    }

    @Test
    fun `should parse properties from properties file`() {
        val path = "src/test/resources/properties"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileSet()),
            analysisProfile = analysisProfileSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile shouldHaveSize 3
        newContext.analysisProfile.map { it.properties }.toSet() shouldBe propertiesSet()
    }
}
