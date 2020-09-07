package com.github.edineipiovesan.parser

import java.io.File
import java.util.*

interface ConfigFileParser {
    fun parse(file: File): Properties
}
