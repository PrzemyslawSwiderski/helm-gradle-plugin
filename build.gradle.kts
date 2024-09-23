plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.pluginPublish)
    alias(libs.plugins.changelog)
}

repositories {
    mavenLocal()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    testImplementation(libs.jupiter)
    testImplementation(libs.jupiterParams)
    testImplementation(libs.assertj)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks {
    test {
        useJUnitPlatform()
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

// Configuring changelog Gradle plugin https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups = listOf("Added", "Changed", "Removed")
}

