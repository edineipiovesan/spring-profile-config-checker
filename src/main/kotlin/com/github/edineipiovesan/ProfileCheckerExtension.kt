package com.github.edineipiovesan

open class ProfileCheckerExtension {
    var enabled: Boolean = true //FIXME: implement on and off config
    var validate: Set<String> = emptySet()
    var dirs: Set<String> = emptySet()
    var ignoreProfiles: Set<String> = emptySet()
}