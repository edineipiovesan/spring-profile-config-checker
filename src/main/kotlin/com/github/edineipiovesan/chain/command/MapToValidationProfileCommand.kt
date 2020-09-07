package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.ValidationProfile
import com.github.edineipiovesan.chain.base.Command
import java.io.File


class MapToValidationProfileCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val analysisProfileSet = HashSet<ValidationProfile>()

        context.propertiesFiles.forEach { entry ->
            val analysisProfile = entry.value
                .map { ValidationProfile(name = getProfile(it), directoryPath = it.parent, file = it) }
                .toSet()
            analysisProfileSet.addAll(analysisProfile)
        }

        return context.copy(analysisProfile = analysisProfileSet)
    }

    override fun executeAfter(context: ValidationContext): ValidationContext {
        println("Command: ${this.javaClass.simpleName}")
        println(context.toString())

        return context
    }

    private fun getProfile(file: File): String {
        if (!file.isFile)
            throw IllegalStateException("Path ${file.path} must be a file")
        val profile = file.name
            .substringAfterLast("-")
            .substringBeforeLast(".")

        return when (profile) {
            "application" -> "default"
            else -> profile
        }
    }
}