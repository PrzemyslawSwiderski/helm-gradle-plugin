rootProject.name = "helm-gradle-plugin"

include("examples:sample-helm-project",
        "examples:sample-helm-project-groovy-dsl")

pluginManagement {
    repositories {
        mavenLocal()
        jcenter()
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
}