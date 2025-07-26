package depConfig

import ext.composeAndroidPreview
import ext.composeDesktopCurrentOs
import ext.composeFullLibrary
import ext.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configurationCompose(
    extension: KotlinMultiplatformExtension
){
    extension.apply {
        sourceSets.named("commonMain") {
            dependencies {
                implementation(libs.composeFullLibrary())
            }
        }

        sourceSets.named("androidMain") {
            dependencies {
                implementation(libs.composeAndroidPreview())
            }
        }

        sourceSets.named("desktopMain") {
            dependencies {
                implementation(libs.composeDesktopCurrentOs())
            }
        }

    }


}