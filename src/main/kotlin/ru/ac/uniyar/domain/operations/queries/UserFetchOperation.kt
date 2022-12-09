package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.BusinessmanTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.entities.User

class UserFetchOperation(
    private val database: Database
) {
    fun fetch(userId: Int): User? =
        database.from(UsersTable).select().where { UsersTable.id eq userId }
            .mapNotNull { row -> UsersTable.createEntity(row) }.firstOrNull()
}