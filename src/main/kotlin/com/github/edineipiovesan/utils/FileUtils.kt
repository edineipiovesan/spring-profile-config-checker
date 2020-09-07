package com.github.edineipiovesan.utils

import com.github.edineipiovesan.parser.ConfigFileParser
import com.github.edineipiovesan.parser.PropertiesParser
import com.github.edineipiovesan.parser.YAMLParser
import java.io.File

fun getPropertyParser(files: Set<File>): ConfigFileParser {
    val extensions = files
        .filter { it.isFile }
        .map { it.extension }
        .filterNot { it.isBlank() }
        .toSet()

    val allSameExtension = extensions.size == 1
    if (!allSameExtension)
        throw IllegalStateException("All property files from directory must have the same extension")

    return when (extensions.first()) {
        "properties" -> PropertiesParser()
        "yaml", "yml" -> YAMLParser()
        else -> throw IllegalStateException("Parser not implemented for this property file extension")
    }
}


fun getProfiles(files: Set<File>): Set<Pair<String, File>> {
    return files.map { Pair(getProfile(it), it) }.toSet()
}

fun filterProfiles(propertyFiles: Set<File>, ignoreProfiles: Set<String>): Set<File> {
    return propertyFiles.filter { !ignoreProfiles.contains(getProfile(it)) }.toSet()
}

fun getProfile(file: File): String {
    if (!file.isFile)
        throw IllegalStateException("File parameter must be a file")
    val profile = file.name
        .substringAfterLast("-")
        .substringBeforeLast(".")

    return when (profile) {
        "application" -> "default"
        else -> profile
    }
}

/***
 * Private helper functions
 */



