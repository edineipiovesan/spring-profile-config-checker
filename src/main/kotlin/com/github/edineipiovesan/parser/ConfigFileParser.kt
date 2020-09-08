package com.github.edineipiovesan.parser

import java.io.File
import java.util.Properties

interface ConfigFileParser {
    fun parse(file: File): Properties
}
