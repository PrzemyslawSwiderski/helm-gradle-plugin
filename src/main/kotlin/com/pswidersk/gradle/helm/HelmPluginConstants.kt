@file:JvmName("HelmPluginConstants")

package com.pswidersk.gradle.helm

/**
 * Version of helm exec to be downloaded.
 * Check https://github.com/helm/helm/releases for available versions.
 */
const val HELM_VERSION = "4.0.0"

/**
 * Name of helm plugin extension in projects.
 */
const val HELM_PLUGIN_EXTENSION_NAME = "helmPlugin"

/**
 * Directory where gradle specific files are stored.
 */
const val GRADLE_FILES_DIR = ".gradle"

/**
 * Directory where helm client will be downloaded and stored.
 */
const val HELM_SETUP_DIR = "helmClient"

/**
 * Plugin tasks group name.
 */
const val PLUGIN_TASKS_GROUP_NAME = "helm"

/**
 * Name of task to set up helm.
 */
const val HELM_SETUP_TASK_NAME = "helmSetup"

