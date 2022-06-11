import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import kotlin.script.templates.standard.ScriptTemplateWithArgs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.04"

project {

    vcsRoot(MyVcsRoot)

    val bts = sequential {
        buildType(Maven("Build", "clean compile"))
        parallel {
            buildType(Maven("FastTest", "clean test", "-Dmaven.test.failure.ignore=true -Dtest=*.resources.*Test"))
            buildType(Maven("SlowTest", "clean test", "-Dmaven.test.failure.ignore=true -Dtest=*.services.*Test"))
        }
        buildType(Maven("Package", "clean package", "-DskipTests"))
    }.buildTypes()

    bts.forEach { buildType(iterator)}
    bts.last().triggrers {
        vcs {

        }
    }
}
object MyVcsRoot: GitVcsRoot({
    name = DslContext.getParameter("vcsName")
    url =  DslContext.getParameter("vcsUrl")
    branch = DslContext.getParameter("vcsBranch", "refs/heads/develop")
})


class Maven(name: String, goals: String, runnerArgs: String? = null): BuildType({
    id(name.toExtId())
    this.name = name

    vcs {
        root(MyVcsRoot)
    }

    steps {
        maven {
            this.goals = goals
            this.runnerArgs = runnerArgs
        }
    }
})

