package com.github.edineipiovesan.validator

import java.util.*

fun isPropertyValid(properties: Set<Properties>, property: String): Boolean {
    val profilesWithProperty = properties.filter { !(it[property] as? String).isNullOrBlank() }
    val values = profilesWithProperty.map { it[property] }.toSet()
    return values.size == profilesWithProperty.size
}