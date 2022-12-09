package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.ProjectTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.User

class UsersListOperation(
    private val database: Database
) {
    fun list(): List<User> =
        database
            .from(UsersTable)
            .select()
            .mapNotNull { row -> UsersTable.createEntity(row) }
}