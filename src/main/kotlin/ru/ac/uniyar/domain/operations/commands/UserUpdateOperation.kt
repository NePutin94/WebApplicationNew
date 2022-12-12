package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.UsersTable


class UserUpdateOperation(private val database: Database) {
    fun update(userId: Int, typeId: Int): Int {
        val id = database.update(UsersTable) {
            set(it.type,typeId)
            where { it.id eq userId}
        }
        return id
    }
}