package plugins

import androidx.room.gradle.RoomExtension
import ext.kspPlugin
import ext.libs
import ext.roomCompilerLibrary
import ext.roomPlugin
import ext.roomRuntimeLibrary
import ext.sqliteDriverLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryRoomFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kmp-library-plugin")
            pluginManager.apply(libs.kspPlugin().get().pluginId)
            pluginManager.apply(libs.roomPlugin().get().pluginId)

            extensions.getByType<KotlinMultiplatformExtension>().apply {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(libs.roomRuntimeLibrary())
                    implementation(libs.sqliteDriverLibrary())
                }
            }
            dependencies {
                val kspTargets = listOf(
                    "kspAndroid",
                    "kspIosSimulatorArm64",
                    "kspIosArm64",
                    "kspIosX64",
                    "kspDesktop"
                )
                kspTargets.forEach { add(it, libs.roomCompilerLibrary()) }
            }

            extensions.getByType<RoomExtension>().apply {
                schemaDirectory(
                    "$projectDir/schemas"
                )
            }
        }
    }

}