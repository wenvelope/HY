package depConfig

import ext.libs
import ext.udfLibrary
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configurationUdf(
    extension: KotlinMultiplatformExtension
){
    extension.apply {
        sourceSets.named("commonMain") {
            dependencies {
                implementation(libs.udfLibrary())
            }
        }
    }
}