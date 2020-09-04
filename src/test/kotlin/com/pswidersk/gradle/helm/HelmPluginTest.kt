package com.pswidersk.gradle.helm

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class HelmPluginTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `test if main external plugins were successfully applied`() {
        val project: Project = ProjectBuilder.builder().build()
        project.pluginManager.apply(HelmPlugin::class.java)

        assertEquals(1, project.plugins.size)
        assertEquals(1, project.tasks.size)
    }

    @Test
    fun `test if terraform setup and version check was successful`() {
        // given
        val expectedOutputMsg = "v3.3.1+g249e521"
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText("""
            import com.pswidersk.gradle.helm.HelmTask
            
            plugins {
                id("com.pswidersk.helm-plugin")
            }
            
            tasks {
                register<HelmTask>("runHelmVersionCheck") {
                    args("version", "--short")
                }
            }
        """.trimIndent())
        val runner = GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(tempDir)
                .forwardOutput()
                .withArguments(":runHelmVersionCheck")

        // when
        val firstRunResult = runner.build()
        val secondRunResult = runner.build()

        // then
        with(firstRunResult) {
            assertEquals(TaskOutcome.SUCCESS, task(":helmSetup")!!.outcome)
            assertEquals(TaskOutcome.SUCCESS, task(":runHelmVersionCheck")!!.outcome)
            Assertions.assertTrue { output.contains(expectedOutputMsg) }
        }
        with(secondRunResult) {
            assertEquals(TaskOutcome.SKIPPED, task(":helmSetup")!!.outcome)
            assertEquals(TaskOutcome.SUCCESS, task(":runHelmVersionCheck")!!.outcome)
            Assertions.assertTrue { output.contains(expectedOutputMsg) }
        }
    }
}