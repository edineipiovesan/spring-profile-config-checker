package com.github.edineipiovesan.chain.command

import com.github.edineipiovesan.chain.ValidationContext
import com.github.edineipiovesan.chain.base.Command
import java.io.File

class ListFilesCommand : Command<ValidationContext> {
    override fun execute(context: ValidationContext): ValidationContext {
        val map = hashMapOf<String, Set<File>>()
        context.extension.dirsPath.forEach {
            val files = getFiles(it)
            map[it] = files
        }

        return context.copy(propertiesFiles = map)
    }

    override fun executeAfter(context: ValidationContext): ValidationContext {
        println("Command: ${this.javaClass.simpleName}")
        println(context.toString())

        return context
    }

//    override fun executeAfter(context: ValidationContext): ValidationContext {
//        if (!context.extension.showLogs) return context
//
//        context.propertiesFiles.forEach { entry ->
//            println("Directory ${entry.key}")
//            println(entry.value.joinToString(separator = ", ") { it.name })
//        }
//
//        return context
//    }

    private fun getFiles(directoryPath: String): Set<File> {
        val directory = getDirectory(directoryPath)
        return directory.listFiles().filter { it.isFile }.toSet()
    }

    private fun getDirectory(directoryPath: String): File {
        val directory = File(directoryPath)
        if (!directory.isDirectory)
            throw IllegalStateException("Path must be a directory")
        return directory
    }
}