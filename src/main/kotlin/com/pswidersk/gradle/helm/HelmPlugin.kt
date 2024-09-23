package com.pswidersk.gradle.helm

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class HelmPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        extensions.create(HELM_PLUGIN_EXTENSION_NAME, HelmPluginExtension::class.java, project)
        tasks.register<ListPropertiesTask>("listPluginProperties")
        tasks.register<HelmSetupTask>("helmSetup")
    }

}
