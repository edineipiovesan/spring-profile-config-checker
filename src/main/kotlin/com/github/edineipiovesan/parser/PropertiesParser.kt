package com.github.edineipiovesan.parser

import java.io.File
import java.io.FileInputStream
import java.util.Properties

class PropertiesParser : ConfigFileParser {
    override fun parse(file: File): Properties {
        val fileInputStream = FileInputStream(file)
        val properties = Properties()
        properties.load(fileInputStream)

        return properties
    }
}
