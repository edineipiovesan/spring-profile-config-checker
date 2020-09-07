package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.ValidationProfile
import com.github.edineipiovesan.chain.base.Command

class ExcludeIgnoredProfilesCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val analysisProfileSet = HashSet<ValidationProfile>()
        val ignoredProfiles = context.extension.ignoreProfiles
            .filterNot { it.equals("default", ignoreCase = true) }
        analysisProfileSet.addAll(context.analysisProfile
            .filterNot { ignoredProfiles.contains(it.name) })

        return context.copy(analysisProfile = analysisProfileSet)
    }

    override fun executeAfter(context: ValidationContext): ValidationContext {
        println("Command: ${this.javaClass.simpleName}")
        println(context.toString())

        return context
    }
}