package ext

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

internal fun VersionCatalog.udfLibrary() =
    findLibrary("udf").get()

internal fun VersionCatalog.composeFullLibrary() =
    findBundle("compose-full").get()

internal fun VersionCatalog.koinFullLibrary() =
    findBundle("koin").get()

internal fun VersionCatalog.roomCompilerLibrary() =
    findLibrary("room-compiler").get()

internal fun VersionCatalog.roomRuntimeLibrary() =
    findLibrary("room-runtime").get()

internal fun VersionCatalog.sqliteDriverLibrary() =
    findLibrary("sqlite-bundled").get()

internal fun VersionCatalog.composeAndroidPreview() =
    findLibrary("compose-androidx-preview").get()

internal fun VersionCatalog.composeDesktopCurrentOs()=
    findLibrary("compose-desktop-currentOs").get()

internal fun VersionCatalog.kotlinSerializationLibrary() =
    findLibrary("kotlinx-serialization").get()

internal fun VersionCatalog.androidApplicationPlugin(): Provider<PluginDependency> =
    findPlugin("androidApplication").get()

internal fun VersionCatalog.androidLibraryPlugin(): Provider<PluginDependency> =
    findPlugin("androidLibrary").get()

internal fun VersionCatalog.jetbrainsComposePlugin(): Provider<PluginDependency> =
    findPlugin("jetbrainsCompose").get()

internal fun VersionCatalog.composeCompilerPlugin(): Provider<PluginDependency> =
    findPlugin("compose-compiler").get()

internal fun VersionCatalog.kotlinMultiplatformPlugin(): Provider<PluginDependency> =
    findPlugin("kotlinMultiplatform").get()

internal fun VersionCatalog.kotlinSerializationPlugin(): Provider<PluginDependency> =
    findPlugin("kotlinSerialization").get()

internal fun VersionCatalog.roomPlugin(): Provider<PluginDependency> =
    findPlugin("room_plugin").get()

internal fun VersionCatalog.kspPlugin(): Provider<PluginDependency> =
    findPlugin("ksp").get()