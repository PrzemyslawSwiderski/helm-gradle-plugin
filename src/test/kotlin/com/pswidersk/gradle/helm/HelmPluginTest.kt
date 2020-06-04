package com.pswidersk.gradle.helm

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HelmPluginTest {

    @Test
    fun `test if main external plugins were successfully applied`() {
        val project: Project = ProjectBuilder.builder().build()
        project.pluginManager.apply(HelmPlugin::class.java)

        assertEquals(1, project.plugins.size)
    }
}