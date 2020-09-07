package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.ValidationProfile
import com.github.edineipiovesan.chain.base.Command

class ApplyDefaultValuesCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val propertiesWithDefault = HashSet<ValidationProfile>()
        val propertiesByDirectory = context.analysisProfile.groupBy { it.directoryPath }

        propertiesByDirectory.forEach { entry ->
            val defaultProperties = entry.value.find { it.name.equals("default", ignoreCase = true) }
            entry.value.forEach { profile ->
                defaultProperties?.properties?.forEach { default ->
                    profile.properties.putIfAbsent(default.key, default.value)
                }
            }
            propertiesWithDefault.addAll(entry.value)
        }

        return context.copy(analysisProfile = propertiesWithDefault)
    }

    override fun executeAfter(context: ValidationContext): ValidationContext {
        println("Command: ${this.javaClass.simpleName}")
        println(context.toString())

        return context
    }
}
