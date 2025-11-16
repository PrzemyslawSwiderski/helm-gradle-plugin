rootProject.name = "helm-gradle-plugin"

include(
    "examples:sample-helm-project",
    "examples:sample-helm-project-groovy-dsl"
)

pluginManagement {
    val pluginVersionForExamples: String by settings

    plugins {
        id("com.pswidersk.helm-plugin") version pluginVersionForExamples
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
