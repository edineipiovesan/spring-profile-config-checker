package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.ValidationProfile
import com.github.edineipiovesan.chain.base.Command
import java.util.*

class FilterValidationPropertiesCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val filteredValidationProperties = HashSet<ValidationProfile>()
        val currentProperties = context.analysisProfile
        val propertiesToValidate = context.extension.validateProps

        currentProperties.forEach { validationProfile ->
            val properties = validationProfile.properties.filterProperties(propertiesToValidate)
            filteredValidationProperties.add(validationProfile.copy(properties = properties))
        }

        return context.copy(analysisProfile = filteredValidationProperties)
    }

    override fun executeAfter(context: ValidationContext): ValidationContext {
        println("Command: ${this.javaClass.simpleName}")
        println(context.toString())

        return context
    }

    private fun Properties.filterProperties(properties: Set<String>): Properties {
        val filteredProperties = Properties()
        this.filter { entry -> properties.contains(entry.key) }
            .map { filteredProperties.put(it.key, it.value) }
        return filteredProperties
    }
}
