package ru.ac.uniyar.domain.database

import org.ktorm.database.Database
import org.ktorm.support.mysql.MySqlDialect

fun connectToDatabase() = Database.connect(
    url = H2DatabaseManager.JDBC_CONNECTION,
    driver = "org.h2.Driver",
    user = "sa",
    dialect = MySqlDialect(),
)
