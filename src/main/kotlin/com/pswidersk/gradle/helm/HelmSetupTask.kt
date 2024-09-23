package com.pswidersk.gradle.helm

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.net.URI

open class HelmSetupTask : DefaultTask() {

    private val helmSetupDir = project.helmPlugin.helmSetupDir.get().asFile

    init {
        group = "helm"
        description = "Helm setup task to install helm client"
        onlyIf {
            !helmSetupDir.exists()
        }
    }

    @TaskAction
    fun setup() = with(project) {
        helmSetupDir.mkdirs()
        val helmPackage = helmSetupDir.resolve(helmPlugin.helmPackage.get())
        val helmArchiveInputStream = URI.create(helmPlugin.helmDownloadUrl.get()).toURL()
        logger.quiet("Downloading terraform from: $helmArchiveInputStream ...")
        helmArchiveInputStream.openStream().use { inputStream ->
            helmPackage.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        logger.quiet("Unzipping package to setup dir: $helmSetupDir ...")
        copy {
            it.from(tarTree(helmPackage))
            it.into(helmSetupDir)
        }
        logger.quiet("Helm setup complete.")
    }

}
