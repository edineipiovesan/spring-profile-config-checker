package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.base.Command
import com.github.edineipiovesan.validator.isPropertyValid

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

}
