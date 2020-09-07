package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.ProfileCheckerExtension
import com.github.edineipiovesan.chain.ValidationContext
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.gradle.internal.impldep.com.google.common.io.Files
import org.junit.jupiter.api.Test

class ListFilesCommandTest {
    private val command = ListFilesCommand()

    @Test
    fun `list all files from directory`() {
        val path = "src/test/resources/yaml"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension)

        val newContext = command.execute(context)

        newContext.propertiesFiles.keys shouldHaveSize 1
        newContext.propertiesFiles.keys shouldContainExactlyInAnyOrder setOf(path)
        newContext.propertiesFiles[path]?.size shouldBe 3
        newContext.propertiesFiles[path]?.map { it.name } shouldContainExactlyInAnyOrder
                setOf("application-prd.yaml", "application-qa.yaml", "application.yaml")
    }

    @Test
    fun `should throw an exception for file path`() {
        val path = "src/test/resources/application-qa.yaml"
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension)

        val exception = shouldThrow<IllegalArgumentException> { command.execute(context) }
        exception.message shouldBe "Path $path must be a directory"
    }

    @Test
    fun `should throw an exception for empty directory`() {
        val path = Files.createTempDir().path
        val extension = ProfileCheckerExtension().apply { this.dirsPath = setOf(path) }
        val context = ValidationContext(extension)

        val exception = shouldThrow<IllegalStateException> { command.execute(context) }
        exception.message shouldBe "Directory $path is empty"
    }
}
