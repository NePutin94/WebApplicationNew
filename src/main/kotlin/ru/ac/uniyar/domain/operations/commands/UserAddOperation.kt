package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.InvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.User
import ru.ac.uniyar.domain.entities.UserType
import java.lang.Exception
import java.sql.Date
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDateTime
import java.time.ZoneId


class UserAddOperation(private val database: Database) {

    private val preparedSql = """
        insert into USERS (CREATIONDATE, NAME, PASSWORD, USERTYPE_ID, FEEDBACK)
        values(CURRENT_TIMESTAMP(), ?, HASH('SHA3-256',?,10), ?, ?)
    """.trimIndent()

    fun add(user: User, salt: String): Any? {

        if(database.from(UsersTable).select(UsersTable.name).where { UsersTable.name eq user.name }
                .mapNotNull { row -> row }.isNotEmpty())
        {
            throw Exception("User with this name already exists")
        }
        database.useConnection { connection ->
            connection.prepareStatement(preparedSql,  Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, user.name)
                statement.setString(2, user.password + salt)
                statement.setInt(3, user.type.id)
                statement.setString(4, user.feedback)
                val affectedRows = statement.executeUpdate()
                val res =  statement.generatedKeys
                if (affectedRows >= 1 && res.next()) {
                    return res.getInt(1)
                } else {
                    throw Exception("The insertion occurred incorrectly")
                }
            }
        }
    }
}