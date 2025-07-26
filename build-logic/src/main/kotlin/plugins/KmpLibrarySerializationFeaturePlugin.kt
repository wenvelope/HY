package plugins

import depConfig.configurationSerialization
import ext.kotlinSerializationPlugin
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibrarySerializationFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kmp-library-plugin")

            apply(
                plugin = libs.kotlinSerializationPlugin().get().pluginId
            )

            val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()

            configurationSerialization(kmpExtension)


        }

    }


}