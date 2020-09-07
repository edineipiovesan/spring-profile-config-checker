package com.github.edineipiovesan

import com.github.edineipiovesan.chain.ValidationChain
import com.github.edineipiovesan.chain.ValidationContext
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class ProfileCheckerTask : DefaultTask() {

    @TaskAction
    fun action() {
        val extension = project.extensions.run {
            findByName("profileChecker") as ProfileCheckerExtension
        }

        println("=-=-= Spring Profile Config Checker")
        val context = ValidationContext(extension = extension)
        ValidationChain(context).execute()
        println("=-=-= Done!")
    }
}
