package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.ProfileCheckerExtension
import com.github.edineipiovesan.analysisProfileSet
import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.propertiesFileYamlSet
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test

class ExcludeIgnoredProfilesCommandTest {
    private val command = ExcludeIgnoredProfilesCommand()

    @Test
    fun `should exclude ignored profiles from context`() {
        val path = "src/test/resources"
        val extension = ProfileCheckerExtension().apply {
            this.dirsPath = setOf(path)
            this.ignoreProfiles = setOf("qa")
        }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileYamlSet()),
            analysisProfile = analysisProfileSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile shouldHaveSize 2
        newContext.analysisProfile.map { it.name } shouldContainExactlyInAnyOrder setOf("default", "prd")
    }

    @Test
    fun `should keep profiles when ignored is not defined`() {
        val path = "src/test/resources"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileYamlSet()),
            analysisProfile = analysisProfileSet()
        )

        val newContext = command.execute(context)

        newContext.analysisProfile shouldHaveSize 3
        newContext.analysisProfile.map { it.name } shouldContainExactlyInAnyOrder setOf("default", "qa", "prd")
    }
}
