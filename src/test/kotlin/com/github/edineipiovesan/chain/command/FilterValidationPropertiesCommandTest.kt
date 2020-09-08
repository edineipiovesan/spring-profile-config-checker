package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.ProfileCheckerExtension
import com.github.edineipiovesan.analysisProfileSet
import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.properties
import com.github.edineipiovesan.propertiesFileSet
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test

class FilterValidationPropertiesCommandTest {
    private val command = FilterValidationPropertiesCommand()

    @Test
    fun `should filter by validation props set in extension`() {
        val path = "src/test/resources/properties"
        val extension = ProfileCheckerExtension().apply {
            this.dirsPath = setOf(path)
            this.validateProps = setOf("plugin.version", "parser")
        }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileSet()),
            analysisProfile = analysisProfileSet().map { it.copy(properties = properties()) }.toSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile.flatMap { it.properties.keys }.toSet() shouldHaveSize 2
        newContext.analysisProfile.flatMap { it.properties.keys }.toSet() shouldContainExactlyInAnyOrder
            setOf("plugin.version", "parser")
    }

    @Test
    fun `should not filter when validation props is not set in extension`() {
        val path = "src/test/resources/properties"
        val extension = ProfileCheckerExtension().apply {
            this.dirsPath = setOf(path)
        }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileSet()),
            analysisProfile = analysisProfileSet().map { it.copy(properties = properties()) }.toSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile.flatMap { it.properties.keys }.toSet() shouldHaveSize properties().size
        newContext.analysisProfile.flatMap { it.properties.keys }.toSet() shouldContainExactlyInAnyOrder
            properties().keys
    }
}
