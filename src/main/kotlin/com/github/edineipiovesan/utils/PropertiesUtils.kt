package com.github.edineipiovesan.utils

import java.util.*

fun Properties.filterProperties(properties: Set<String>): Properties {
    val filteredProperties = Properties()
    this.filter { entry -> properties.contains(entry.key) }
        .map { filteredProperties.put(it.key, it.value) }
    return filteredProperties
}