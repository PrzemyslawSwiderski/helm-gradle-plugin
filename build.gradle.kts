import org.jetbrains.changelog.closure
import org.jetbrains.changelog.date
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "1.4.0"
    id("com.gradle.plugin-publish") version "0.11.0"
    id("net.researchgate.release") version "2.8.1"
    id("org.jetbrains.changelog") version "0.5.0"
}

repositories {
    mavenLocal()
    jcenter()
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
    "afterReleaseBuild"{
        dependsOn("publish", "publishPlugins")
    }
    "beforeReleaseBuild"{
        dependsOn("patchChangelog")
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
gradlePlugin {
    plugins {
        create("helm-gradle-plugin") {
            id = "com.pswidersk.helm-plugin"
            implementationClass = "com.pswidersk.gradle.helm.HelmPlugin"
            displayName = "Simple Gradle plugin to wrap Helm executable as task. https://github.com/PrzemyslawSwiderski/helm-gradle-plugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/PrzemyslawSwiderski/helm-gradle-plugin"
    vcsUrl = "https://github.com/PrzemyslawSwiderski/helm-gradle-plugin"
    description = "Simple Gradle plugin to wrap Helm executable as task."
    tags = listOf("helm", "kubernetes", "kubectl")
}

publishing {
    repositories {
        mavenLocal()
    }
}

changelog {
    header = closure { "[${project.version}] - ${date()}" }
}