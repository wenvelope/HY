plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.room.plugin)
    alias(libs.plugins.ksp)
}


kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.coroutines)
            implementation(libs.kotlinx.serialization)
            implementation(libs.room.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqlite.bundled)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
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

