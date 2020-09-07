package com.github.edineipiovesan.parser

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class PropertiesParserTest {
    private val propertiesParser = PropertiesParser()

    @Test
    fun parseProperties() {
        val file = File("src/test/resources/properties/application.properties")
        val properties = propertiesParser.parse(file)

        properties["parser"] shouldBe "properties"
        properties["plugin.author.name"] shouldBe "Edinei Piovesan"
        properties["plugin.author.github"] shouldBe "https://github.com/edineipiovesan"
        properties["plugin.display-name"] shouldBe "Spring Profile Config Checker"
        properties["plugin.name"] shouldBe "spring-profile-config-checker"
        properties["plugin.version"] shouldBe "1.0.0-SNAPSHOT"
    }
}
