package ru.ac.uniyar
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString

data class AppConfig(
    val webPort: Int,
) {
    companion object {
        val webPortLens = EnvironmentKey.int().required("web.port", "Application web port")
        val saltLens = EnvironmentKey.nonEmptyString().required("auth.salt")
    }
}