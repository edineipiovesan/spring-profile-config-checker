package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.base.Command
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Set
import kotlin.collections.filter
import kotlin.collections.forEach
import kotlin.collections.groupBy
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.toSet

class ValidatePropertiesCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val propertiesValidation = HashMap<String, Set<Boolean>>()
        val validateProps = context.extension.validateProps
        val propertiesByDirectory = context.analysisProfile.groupBy { it.directoryPath }

        propertiesByDirectory.forEach { entry ->
            propertiesValidation[entry.key] = validateProps.map {
                isPropertyValid(entry.value.map { it.properties }.toSet(), it)
            }.toSet()
        }

        propertiesValidation.forEach {
            if (it.value.contains(false))
                throw IllegalStateException("Not all properties has different values. Check your properties profiles.")
        }

        return context
    }

    private fun isPropertyValid(properties: Set<Properties>, property: String): Boolean {
        val profilesWithProperty = properties.filter { !(it[property] as? String).isNullOrBlank() }
        val values = profilesWithProperty.map { it[property] }.toSet()
        return values.size == profilesWithProperty.size
    }
}
