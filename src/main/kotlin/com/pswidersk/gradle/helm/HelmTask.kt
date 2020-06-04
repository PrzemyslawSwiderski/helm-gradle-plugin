package com.pswidersk.gradle.helm

import org.apache.tools.ant.types.Commandline
import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.options.Option

open class HelmTask : AbstractExecTask<HelmTask>(HelmTask::class.java) {

    init {
        group = PLUGIN_TASKS_GROUP_NAME
        dependsOn(HELM_SETUP_TASK_NAME)
        executable = helmExec(project, project.helmPlugin.helmVersion.get())
    }

    /**
     * Parses an argument list from {@code args} and passes it to args.
     * It overrides any args passed to task in build script.
     *
     * <p>
     * The parser supports both single quote ({@code '}) and double quote ({@code "}) as quote delimiters.
     * For example, to pass the argument {@code foo bar}, use {@code "foo bar"}.
     * </p>
     * <p>
     * Note: the parser does <strong>not</strong> support using backslash to escape quotes. If this is needed,
     * use the other quote delimiter around it.
     * For example, to pass the argument {@code 'singly quoted'}, use {@code "'singly quoted'"}.
     * </p>
     *
     * @param args Args for the main class. Will be parsed into an argument list.
     * @return this
     */
    @Option(option = "args", description = "Command line arguments overriding execArgs.")
    fun setArgsByCmd(args: String) = this.setArgs(Commandline.translateCommandline(args).toList())

}