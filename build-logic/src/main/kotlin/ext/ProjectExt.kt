package ext

import config.ConfigurationKeys
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

private fun Project.isEligibleForCoverage(): Boolean {
    return ConfigurationKeys.ELIGIBLE_MODULES_FOR_COVERAGE.contains(name)
}



internal val Project.libs: VersionCatalog
    get() {
        return project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs")
    }