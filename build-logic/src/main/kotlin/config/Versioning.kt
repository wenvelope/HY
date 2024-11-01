package config

internal object Versioning {
    private const val LOCAL_VERSION_CODE = 1
    private const val LOCAL_VERSION_NAME = "1.0.0"
    private const val MAJOR_VERSION = "1.8"

    internal fun versionCode(): Int {
        return LOCAL_VERSION_CODE
    }

    internal fun versionName(): String {
        return LOCAL_VERSION_NAME
    }
}