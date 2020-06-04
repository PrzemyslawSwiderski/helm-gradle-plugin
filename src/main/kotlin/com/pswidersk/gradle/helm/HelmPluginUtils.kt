package com.pswidersk.gradle.helm

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property


/**
 * Creates a [Property] to hold values of the given type.
 *
 * @param T the type of the property
 * @return the property
 */
internal inline fun <reified T : Any> ObjectFactory.property(): Property<T> =
        property(T::class.javaObjectType)

/**
 * Gets the [HelmPluginExtension] that is installed on the project.
 */
internal val Project.helmPlugin: HelmPluginExtension
    get() = extensions.getByType(HelmPluginExtension::class.java)


/**
 * Returns simplified operating system name
 */
internal fun os(): String {
    return when {
        Os.isFamily(Os.FAMILY_MAC) -> "darwin"
        Os.isFamily(Os.FAMILY_WINDOWS) -> "windows"
        else -> "linux"
    }
}

/**
 * Returns system architecture name
 */
internal fun arch(): String {
    val arch = System.getProperty("os.arch")
    return if (arch == "x86_64") {
        "amd64"
    } else {
        arch
    }
}

internal fun helmSetupDir(project: Project, helmVersion: String) = project.rootDir.resolve(GRADLE_FILES_DIR).resolve("$HELM_SETUP_DIR-v$helmVersion")

internal fun helmExec(project: Project, helmVersion: String) = "${helmSetupDir(project, helmVersion)}/${os()}-${arch()}/helm"