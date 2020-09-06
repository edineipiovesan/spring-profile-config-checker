package com.github.edineipiovesan

import org.gradle.api.Plugin
import org.gradle.api.Project

class SpringProfileConfigChecker : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("profileChecker", ProfileCheckerExtension::class.java)

        with(project.tasks) {
            create("profileChecker", ProfileCheckerTask::class.java) {
                it.group = "Verification"
                it.description = "Check if properties has the same value to different profiles"
            }
        }
    }
}
