package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.Profile
import com.github.edineipiovesan.Profile.DEFAULT
import com.github.edineipiovesan.Profile.PRD
import com.github.edineipiovesan.Profile.QA
import com.github.edineipiovesan.ProfileCheckerExtension
import com.github.edineipiovesan.analysisProfileSet
import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.propertiesFileSet
import com.github.edineipiovesan.validateProperties
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ApplyDefaultValuesCommandTest {
    private val command = ApplyDefaultValuesCommand()

    @Test
    fun `should apply values from default profile when it is missing in any other profiles`() {
        val path = "src/test/resources/properties"
        val extension = ProfileCheckerExtension().apply {
            this.dirsPath = setOf(path)
            this.validateProps = setOf("plugin.version", "plugin.default-value", "parser")
        }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileSet()),
            analysisProfile = analysisProfileSet()
                .map {
                    it.copy(
                        properties = validateProperties(
                            Profile.valueOf(it.name.toUpperCase()),
                            *extension.validateProps.toTypedArray()
                        )
                    )
                }.toSet()
        )
        val profiles = setOf(QA, PRD).map { it.name.toLowerCase() }.toSet()

        val newContext = command.execute(context)

        newContext.analysisProfile
            .filter { profiles.contains(it.name) }
            .map { Pair(it.name, it.properties) }
            .forEach {
                it.second.keys shouldHaveSize extension.validateProps.size
                it.second.keys shouldContainExactlyInAnyOrder extension.validateProps
                if (setOf(DEFAULT, QA).contains(Profile.valueOf(it.first.toUpperCase())))
                    it.second["plugin.default-value"] shouldBe "this-is-${it.first}-value"
                else
                    it.second["plugin.default-value"] shouldBe "this-is-default-value"
            }
    }

    @Test
    fun `should not override a profile value`() {
        val path = "src/test/resources/properties"
        val extension = ProfileCheckerExtension().apply {
            this.dirsPath = setOf(path)
            this.validateProps = setOf("plugin.version", "plugin.default-value", "parser")
        }
        val context = ValidationContext(extension).copy(
            propertiesFiles = hashMapOf(path to propertiesFileSet()),
            analysisProfile = analysisProfileSet()
                .map {
                    it.copy(
                        properties = validateProperties(
                            Profile.valueOf(it.name.toUpperCase()),
                            *extension.validateProps.toTypedArray()
                        )
                    )
                }.toSet()
        )
        val profiles = setOf(QA, PRD).map { it.name.toLowerCase() }.toSet()

        val newContext = command.execute(context)

        val newContextProperties = newContext.analysisProfile
            .filter { profiles.contains(it.name) }
            .map { it.properties }

        newContextProperties.forEach {
            it.keys shouldHaveSize extension.validateProps.size
            it.keys shouldContainExactlyInAnyOrder extension.validateProps
        }

        newContextProperties
            .map { it.filterKeys { key -> key == "plugin.default-value" } }
            .flatMap { it.values } shouldContainExactlyInAnyOrder setOf("this-is-default-value", "this-is-qa-value")
    }
}
