package com.github.edineipiovesan.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File
import java.util.Properties
import kotlin.collections.set

class YAMLParser : ConfigFileParser {
    override fun parse(file: File): Properties {
        val mapper = ObjectMapper(YAMLFactory()).findAndRegisterModules()
        val hashMap = mapper.readValue(file, HashMap::class.java)

        val flattenProperties = Properties()
        hashMapToFlattenProperties(hashMap, flattenProperties)

        return flattenProperties
    }

    private fun hashMapToFlattenProperties(propsOrigin: HashMap<*, *>, propsDest: Properties, prefix: String? = null) {
        propsOrigin.forEach {
            val key = if (prefix.isNullOrBlank()) it.key as String else "$prefix.${it.key}"
            if (it.value is HashMap<*, *>) {
                hashMapToFlattenProperties(it.value as HashMap<*, *>, propsDest, key)
            } else {
                propsDest[key] = it.value
            }
        }
    }
}
