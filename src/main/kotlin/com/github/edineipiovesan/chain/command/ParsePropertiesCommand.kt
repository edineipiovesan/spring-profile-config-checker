package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.ValidationProfile
import com.github.edineipiovesan.chain.base.Command
import com.github.edineipiovesan.parser.ConfigFileParser
import com.github.edineipiovesan.parser.PropertiesParser
import com.github.edineipiovesan.parser.YAMLParser
import java.io.File
import java.util.*

class ParsePropertiesCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val profileProperties = HashSet<ValidationProfile>()
        val analysisProfile = context.analysisProfile

        analysisProfile.forEach { validationProfile ->
            val parser = getPropertyParser(validationProfile.file)
            val properties = parser.parse(validationProfile.file)
            profileProperties.add(validationProfile.copy(properties = properties))
        }

        return context.copy(analysisProfile = profileProperties)
    }

    override fun executeAfter(context: ValidationContext): ValidationContext {
        println("Command: ${this.javaClass.simpleName}")
        println(context.toString())

        return context
    }

    private fun getPropertyParser(file: File): ConfigFileParser {
        if (!file.isFile)
            throw IllegalStateException("File parameter must be a file")

        return when (file.extension) {
            "properties" -> PropertiesParser()
            "yaml", "yml" -> YAMLParser()
            else -> throw IllegalStateException("Parser not implemented for this property file extension")
        }
    }
}