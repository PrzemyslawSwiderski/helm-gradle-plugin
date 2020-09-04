package com.pswidersk.gradle.helm

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER")
open class HelmPluginExtension @Inject constructor(project: Project,
                                                   objects: ObjectFactory) {

    val helmVersion: Property<String> = objects.property<String>().convention(HELM_VERSION)

}