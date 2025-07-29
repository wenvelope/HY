package plugins

import com.google.devtools.ksp.gradle.KspExtension
import depConfig.configurationKoin
import ext.koinCompilerLibrary
import ext.kspPlugin
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpApplicationKoinFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kmp-app-plugin")
            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            configurationKoin(extension)
        }
    }
}
