package depConfig

import ext.koinCompilerLibrary
import ext.koinFullLibrary
import ext.kspPlugin
import ext.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
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

internal fun Project.configurationKoinKspCompiler() {
    pluginManager.apply(libs.kspPlugin().get().pluginId)
    val extension = extensions.getByType<KotlinMultiplatformExtension>()
    extension.apply{
        sourceSets.named("commonMain").configure {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
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
    val kspExtension = extensions.getByType<com.google.devtools.ksp.gradle.KspExtension>()
    kspExtension.apply {
        arg("KOIN_CONFIG_CHECK", "true")
        arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
    }

    tasks.configureEach {
        if (name != "kspCommonMainKotlinMetadata" && name.startsWith("ksp")) {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}
