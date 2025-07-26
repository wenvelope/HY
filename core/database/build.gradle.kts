plugins {
    alias (libs.plugins.kmp.library.serialization.feature.plugin)
    alias(libs.plugins.kmp.library.koin.feature.plugin)
    alias(libs.plugins.room.plugin)
    alias(libs.plugins.ksp)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.room.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqlite.bundled)
        }
    }
}


dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspDesktop", libs.room.compiler)
}


room {
    schemaDirectory("$projectDir/schemas")
}

