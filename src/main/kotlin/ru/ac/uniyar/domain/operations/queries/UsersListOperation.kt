package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.UserTypesEnum
import ru.ac.uniyar.domain.entities.User

class UsersListOperation(
    private val database: Database
) {
    fun list(): List<User> =
        database
            .from(UsersTable)
            .select()
            .mapNotNull { row -> UsersTable.createEntity(row) }

    fun list(uType: UserTypesEnum): List<User> =
        database
            .from(UsersTable)
            .select()
            .where { UsersTable.type eq uType.value }
            .mapNotNull { row -> UsersTable.createEntity(row) }
}