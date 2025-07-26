package depConfig

import ext.composeFullLibrary
import ext.kotlinSerializationLibrary
import ext.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configurationSerialization(
    extension: KotlinMultiplatformExtension
){
    extension.apply {
        sourceSets.named("commonMain") {
            dependencies {
                implementation(libs.kotlinSerializationLibrary())
            }
        }
    }
}