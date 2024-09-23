# [Gradle Helm plugin](https://plugins.gradle.org/plugin/com.pswidersk.helm-plugin)

## About

Simple Gradle plugin to wrap [Helm](https://helm.sh/) executable as tasks.

By using this plugin there is no need to download and install helm client.
Helm executable is downloaded and unpacked to specific local directory.
All major operating systems such as Linux, Windows, Mac OS are supported.

## Requirements

* JDK 8 or higher and `JAVA_HOME` properly set to run gradle wrapper
* configured Kubernetes context to be used by helm client

## Usage

### Steps to run helm commands by using Gradle

1. Apply a plugin to a project as described
   on [gradle portal](https://plugins.gradle.org/plugin/com.pswidersk.helm-plugin).
2. Configure a plugin by specifying desired helm version in build script:
    ```kotlin
    helmPlugin {
        helmVersion.set("3.16.1")
    }
    ```
3. Define a task to run desired helm client command, for example:
    ```kotlin
    tasks {
        register<HelmTask>("helmHelp") {
            args("--help")
        }
    }
    ```
4. Run helm command from gradle:
    ```shell script
    # Linux
    ./gradlew helmHelp
    # Windows
    gradlew.bat helmHelp
    ```

### Additional examples can be found in `examples` module in this project.

## Properties

Plugin behavior can be adjusted by the following properties:

- `helmVersion` - version of Terraform to be downloaded, `1.9.6` by default
- `helmSetupDir` - Helm installation directory, by default
  `<project_dir>/.gradle/helmClient-v<helmVersion>`
- `operatingSystem` - local operating system, `linux` by default
- `architecture` - system CPU architecture, resolved by `os.arch` system property by default
- `helmPackage` - name of the package to download and save as, by default
  `helm-v${helmVersion.get()}-${operatingSystem.get()}-${architecture.get()}.tar.gz`
- `helmDownloadUrl` - can be used to adjust the download URL directly,
  e.g. `https://releases.hashicorp.com/helm/1.9.6/helm_1.9.6_linux_arm64.zip`, by default it is resolved as
  `https://releases.hashicorp.com/helm/${helmVersion.get()}/${helmPackage.get()}`
- `helmExec` - helm executable location, `<helmSetupDir>/${operatingSystem.get()}-${architecture.get()}/helm")`

Sample configuration block can look like:

```kotlin
helmPlugin {
    helmVersion = "4.10.0-alpha"
    helmSetupDir = File("setupDir")
    operatingSystem = "Linux"
    architecture = "arm64"
    helmPackage = "customHelm.zip"
    helmDownloadUrl = "https://proxy-helm/helm.zip"
    helmExec = File("setupDir").resolve("helm")
}
```

## [Changelog](./CHANGELOG.md)
