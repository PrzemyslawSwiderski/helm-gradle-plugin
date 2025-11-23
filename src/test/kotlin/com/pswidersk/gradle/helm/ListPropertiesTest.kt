package com.pswidersk.gradle.helm

import org.apache.tools.ant.taskdefs.condition.Os
import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ListPropertiesTest {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `test if default properties were correctly set`() {
        // given
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText(
            """
            plugins {
                id("com.pswidersk.helm-plugin")
            }
        """.trimIndent()
        )
        val runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(tempDir)
            .forwardOutput()
            .withArguments(":listPluginProperties")
        val expectedSetupPath = tempDir.resolve(".gradle").resolve("helmClient-v4.0.0")
        val expectedPackageName = "helm-v4.0.0-${os()}-${arch()}.tar.gz"
        val expectedExecPath = expectedSetupPath.resolve("${os()}-${arch()}").resolve("helm")

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Helm version: 4.0.0",
                "Setup directory: ${sysDepPath(expectedSetupPath)}",
                "System: ${os()}",
                "Arch: ${arch()}",
                "Helm package: $expectedPackageName",
                "Helm download URL: https://get.helm.sh/$expectedPackageName",
                "Helm exec location: ${sysDepPath(expectedExecPath)}"
            )
        }
    }

    @Test
    fun `test if defaults are overridden by user`() {
        // given
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText(
            """
            plugins {
                id("com.pswidersk.helm-plugin")
            }
            helmPlugin {
                helmVersion = "4.10.0-alpha"
                helmSetupDir = File("setupDir")
                operatingSystem = "Linux"
                architecture = "arm64"
                helmPackage = "customHelm.zip"
                helmDownloadUrl = "https://proxy-helm/helm.zip"
                helmExec = File("setupDir").resolve("helm")
            }
        """.trimIndent()
        )
        val runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(tempDir)
            .forwardOutput()
            .withArguments("--configuration-cache", ":listPluginProperties")
        val expectedSetupPath = tempDir.resolve("setupDir")
        val expectedPackageName = "customHelm.zip"
        val expectedExecPath = expectedSetupPath.resolve("helm")

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Helm version: 4.10.0-alpha",
                "Setup directory: ${sysDepPath(expectedSetupPath)}",
                "System: Linux",
                "Arch: arm64",
                "Helm package: $expectedPackageName",
                "Helm download URL: https://proxy-helm/helm.zip",
                "Helm exec location: ${sysDepPath(expectedExecPath)}"
            )
        }
    }

    private fun sysDepPath(inputFile: File): String {
        return if (Os.isFamily(Os.FAMILY_MAC)) {
            File("/private").resolve(inputFile).absolutePath
        } else {
            inputFile.absolutePath
        }
    }

}
