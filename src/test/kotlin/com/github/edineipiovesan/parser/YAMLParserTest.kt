package com.github.edineipiovesan.parser

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class YAMLParserTest {
    private val yamlParser = YAMLParser()

    @Test
    fun parseYML() {
        val file = File("src/test/resources/yml/application.yml")
        val properties = yamlParser.parse(file)

        properties["parser"] shouldBe "yml"
        properties["plugin.author.name"] shouldBe "Edinei Piovesan"
        properties["plugin.author.github"] shouldBe "https://github.com/edineipiovesan"
        properties["plugin.display-name"] shouldBe "Spring Profile Config Checker"
        properties["plugin.name"] shouldBe "spring-profile-config-checker"
        properties["plugin.version"] shouldBe "1.0.0-SNAPSHOT"
    }

    @Test
    fun parseYAML() {
        val file = File("src/test/resources/yaml/application.yaml")
        val properties = yamlParser.parse(file)

        properties["parser"] shouldBe "yaml"
        properties["plugin.author.name"] shouldBe "Edinei Piovesan"
        properties["plugin.author.github"] shouldBe "https://github.com/edineipiovesan"
        properties["plugin.display-name"] shouldBe "Spring Profile Config Checker"
        properties["plugin.name"] shouldBe "spring-profile-config-checker"
        properties["plugin.version"] shouldBe "1.0.0-SNAPSHOT"
    }
}