package com.pswidersk.gradle.helm

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelmPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(HELM_PLUGIN_EXTENSION_NAME, HelmPluginExtension::class.java, project)
        project.tasks.register("helmSetup", HelmSetupTask::class.java)
    }

}
