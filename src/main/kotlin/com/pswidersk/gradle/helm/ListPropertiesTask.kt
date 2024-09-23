package com.pswidersk.gradle.helm

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import kotlin.text.trimIndent

abstract class ListPropertiesTask : DefaultTask() {

    private val helmExtension: HelmPluginExtension = project.helmPlugin

    init {
        group = "helm"
        description = "List basic plugin properties."
    }

    @TaskAction
    fun setup() {
        with(helmExtension) {
            logger.lifecycle(
                """
                Helm version: ${helmVersion.get()}
                Setup directory: ${helmSetupDir.get()}
                System: ${operatingSystem.get()}
                Arch: ${architecture.get()}
                Helm package: ${helmPackage.get()}
                Helm download URL: ${helmDownloadUrl.get()}
                Helm exec location: ${helmExec.get()}
            """.trimIndent()
            )
        }
    }
}
