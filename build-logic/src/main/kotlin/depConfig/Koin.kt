package depConfig

import ext.koinFullLibrary
import ext.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configurationKoin(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets.named("commonMain") {
            dependencies {
                implementation(libs.koinFullLibrary())
            }
        }
    }
}
