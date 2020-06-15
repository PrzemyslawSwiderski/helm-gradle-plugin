package com.pswidersk.gradle.helm

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URL

open class HelmSetupTask : DefaultTask() {

    init {
        group = "helm"
        description = "Helm setup task to install helm client"
    }

    private val helmSetupDir = helmSetupDir(project, project.helmPlugin.helmVersion.get())

    @TaskAction
    fun setup() {
        val os = os()
        val arch = arch()
        val helmVersion = project.helmPlugin.helmVersion.get()
        if (!helmSetupDir.exists()) {
            helmSetupDir.mkdirs()
            val helmPackage = helmSetupDir.resolve("helm-v$helmVersion-$os-$arch.tar.gz")
            downloadHelmPackage(helmPackage)
            project.copy {
                it.from(project.tarTree(helmPackage))
                it.into(helmSetupDir)
            }
            helmPackage.delete()
        }
    }

    private fun downloadHelmPackage(helmPackage: File) {
        val helmArchiveInputStream = URL("https://get.helm.sh/${helmPackage.name}")
                .openStream()
        helmArchiveInputStream.use { inputStream ->
            helmPackage.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}