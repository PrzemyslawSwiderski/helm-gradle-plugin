package com.pswidersk.gradle.helm

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.internal.file.FileFactory
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER")
open class HelmPluginExtension @Inject constructor(
    project: Project,
    providerFactory: ProviderFactory,
    objects: ObjectFactory,
    fileFactory: FileFactory
) {

    val helmVersion: Property<String> = objects.property<String>().convention(HELM_VERSION)


    val helmSetupDir: DirectoryProperty = objects.directoryProperty().convention(
        providerFactory.provider {
            fileFactory.dir(
                project.rootDir
                    .resolve(GRADLE_FILES_DIR)
                    .resolve("$HELM_SETUP_DIR-v${helmVersion.get()}")
            )
        }
    )

    val operatingSystem: Property<String> = objects.property<String>().convention(os())

    val architecture: Property<String> = objects.property<String>().convention(arch())

    val helmPackage: Property<String> = objects.property<String>().convention(
        "helm-v${helmVersion.get()}-${operatingSystem.get()}-${architecture.get()}.tar.gz"
    )

    val helmDownloadUrl: Property<String> = objects.property<String>().convention(
        "https://get.helm.sh/${helmPackage.get()}"
    )

    val helmExec: RegularFileProperty = objects.fileProperty().convention(
        providerFactory.provider {
            helmSetupDir.get().file("${operatingSystem.get()}-${architecture.get()}/helm")
        }
    )

}
