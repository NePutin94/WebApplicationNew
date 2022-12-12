package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.database.UserTypeTable
import ru.ac.uniyar.domain.entities.UserTypesEnum
import ru.ac.uniyar.domain.entities.UserType

class UserTypeFetch(
    private val database: Database
) {
    fun fetch(userT: UserTypesEnum): UserType? {
        return when (userT) {
            UserTypesEnum.BUSINESSMAN -> database.from(UserTypeTable).select().where { UserTypeTable.id eq UserTypesEnum.BUSINESSMAN.value }
                .mapNotNull { row -> UserTypeTable.createEntity(row) }.firstOrNull()

            UserTypesEnum.AUTHORIZED -> database.from(UserTypeTable).select().where { UserTypeTable.id eq UserTypesEnum.AUTHORIZED.value }
                .mapNotNull { row -> UserTypeTable.createEntity(row) }.firstOrNull()
        }
    }

}