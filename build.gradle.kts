import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.pluginPublish)
}

version = System.getenv("PLUGIN_VERSION") ?: "unspecified"

repositories {
    mavenLocal()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    testImplementation(libs.bundles.test)
}

java {
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    test {
        useJUnitPlatform()
    }
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

gradlePlugin {
    website = "https://github.com/PrzemyslawSwiderski/helm-gradle-plugin"
    vcsUrl = "https://github.com/PrzemyslawSwiderski/helm-gradle-plugin"
    plugins {
        create("helm-gradle-plugin") {
            id = "com.pswidersk.helm-plugin"
            implementationClass = "com.pswidersk.gradle.helm.HelmPlugin"
            displayName = "Simple Plugin to wrap Helm executable as task."
            description = "Simple Plugin to wrap Helm executable as task."
            tags = listOf("helm", "kubernetes", "kubectl")
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

