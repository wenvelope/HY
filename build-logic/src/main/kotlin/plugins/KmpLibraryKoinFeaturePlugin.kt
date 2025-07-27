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

class KmpLibraryKoinFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kmp-library-plugin")
            pluginManager.apply(libs.kspPlugin().get().pluginId)
            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            configurationKoin(extension)

            dependencies {
                val kspTargets =
                    listOf(
                        "kspCommonMainMetadata",
                        "kspAndroid",
                        "kspIosSimulatorArm64",
                        "kspIosArm64",
                        "kspIosX64",
                        "kspDesktop",
                    )
                kspTargets.forEach { add(it, libs.koinCompilerLibrary()) }
            }

            val kspExtension = extensions.getByType<KspExtension>()

            kspExtension.apply {
                arg("KOIN_CONFIG_CHECK", "true")
                arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
            }

            tasks
                .matching { it.name == "compileKotlinMetadata" }
                .configureEach {
                    dependsOn("kspCommonMainKotlinMetadata")
                }
        }
    }
}
