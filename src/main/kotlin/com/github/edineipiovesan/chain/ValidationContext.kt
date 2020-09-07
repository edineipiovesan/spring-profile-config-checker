package com.github.edineipiovesan.chain

import com.github.edineipiovesan.ProfileCheckerExtension
import com.github.edineipiovesan.chain.base.Context
import java.io.File
import java.util.*

data class ValidationContext(
    val extension: ProfileCheckerExtension,
    val propertiesFiles: Map<String, Set<File>> = emptyMap(),
    val analysisProfile: Set<ValidationProfile> = emptySet()
) : Context

data class ValidationProfile(
    val name: String,
    val file: File,
    val directoryPath: String,
    val properties: Properties = Properties()
)
