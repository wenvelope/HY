package plugins

import depConfig.configurationUdf
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryUdfFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(
                "kmp-library-plugin",
            )

            val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()

            configurationUdf(kmpExtension)
        }
    }
}
