package ru.ac.uniyar.domain.database

import org.flywaydb.core.Flyway
import ru.ac.uniyar.domain.database.H2DatabaseManager

fun performMigrations() {
    val flyway = Flyway
        .configure()
        .locations("db/migrations")
        .validateMigrationNaming(true)
        .dataSource(H2DatabaseManager.JDBC_CONNECTION, "sa", null)
        .load()
    flyway.migrate()
}