package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.UserTypeTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.TypesEnum
import ru.ac.uniyar.domain.entities.User
import ru.ac.uniyar.domain.entities.UserType

class UserTypeFetch(
    private val database: Database
) {
    fun fetch(userT: TypesEnum): UserType? {
        return when (userT) {
            TypesEnum.BUSINESSMAN -> database.from(UserTypeTable).select().where { UserTypeTable.id eq TypesEnum.BUSINESSMAN.value }
                .mapNotNull { row -> UserTypeTable.createEntity(row) }.firstOrNull()

            TypesEnum.AUTHORIZED -> database.from(UserTypeTable).select().where { UserTypeTable.id eq TypesEnum.AUTHORIZED.value }
                .mapNotNull { row -> UserTypeTable.createEntity(row) }.firstOrNull()
        }
    }

}