package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.mapNotNull
import ru.ac.uniyar.domain.database.ProjectTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.User
import java.sql.Date
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDateTime
import java.time.ZoneId


class UserAddOperation(private val database: Database) {
    fun add(user: User): Any {
        val id = database.insertAndGenerateKey(UsersTable) {
            set(it.name, user.name)
            set(it.creationdate, user.creationDate)
            set(it.password, user.password)
        }
        return id
    }

    private val preparedSql = """
        insert into USERS (CREATIONDATE, NAME, PASSWORD, TYPE)
        values(CURRENT_TIMESTAMP(), 'Test', HASH('SHA3-256', '12345', 10), 1)
    """.trimIndent()

    fun add(name: String, password: String, type: Int, salt: String): Any? {
        database.useConnection { connection ->
            connection.prepareStatement(preparedSql,  Statement.RETURN_GENERATED_KEYS).use { statement ->
//                statement.setString(1, name)
//                statement.setString(2, password + salt)
//                statement.setInt(3, type)
                val affectedRows = statement.executeUpdate()
                val res =  statement.getGeneratedKeys();
                if (res.next()) {
                    return res.getLong(1);
                } else {
                    // Throw exception?
                }
                return statement.generatedKeys;
            }
        }
    }
}