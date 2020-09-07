package com.github.edineipiovesan

open class ProfileCheckerExtension {
    var enabled: Boolean = true // FIXME: implement on and off config
    var showLogs: Boolean = false
    var dirsPath: Set<String> = emptySet()
    var validateProps: Set<String> = emptySet()
    var ignoreProfiles: Set<String> = emptySet()
}
