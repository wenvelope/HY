package plugins

import depConfig.configurationCompose
import ext.composeCompilerPlugin
import ext.jetbrainsComposePlugin
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpApplicationComposeFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kmp-app-plugin")
            apply(plugin = libs.jetbrainsComposePlugin().get().pluginId)
            apply(plugin = libs.composeCompilerPlugin().get().pluginId)
            val multiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>()
            configurationCompose(multiplatformExtension)
        }
    }
}
