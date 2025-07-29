package plugins

import depConfig.configurationKoin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryKoinFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kmp-library-plugin")
            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            configurationKoin(extension)
        }
    }
}
