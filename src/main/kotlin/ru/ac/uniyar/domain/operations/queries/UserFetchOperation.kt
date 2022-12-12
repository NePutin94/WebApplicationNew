package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.BusinessmanTable
import ru.ac.uniyar.domain.database.UserTypeTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.entities.User
import ru.ac.uniyar.domain.entities.UserType
import java.sql.Statement

class UserFetchOperation(
    private val database: Database
) {
    fun fetch(userId: Int): User? =
        database.from(UsersTable)
            .joinReferencesAndSelect()
            .where { UsersTable.id eq userId }
            .mapNotNull { row -> UsersTable.createEntity(row) }.firstOrNull()

    private val preparedSql = """
        SELECT * FROM USERS WHERE NAME=? AND PASSWORD=HASH('SHA3-256',?,10) ORDER BY ID
    """.trimIndent()

    fun fetch(name: String, password: String, salt: String): String? {
        database.useConnection { connection ->
            connection.prepareStatement(preparedSql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, name)
                statement.setString(2, password + salt)
                return statement
                    .executeQuery()
                    .asIterable()
                    .map { row ->
                        row.getString(1)
                    }.firstOrNull()
            }
        }
    }
}