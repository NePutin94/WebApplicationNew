package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.database.BusinessmanTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.User

class BusinessmanListOperation(
    private val database: Database,
) {
    fun listFiltered(filter: () -> ColumnDeclaring<Boolean>): List<Businessman> =
        database
            .from(BusinessmanTable)
            .joinReferencesAndSelect()
            .where(filter)
            .orderBy(BusinessmanTable.creationdate.desc())
            .mapNotNull { row -> BusinessmanTable.createEntity(row) }

    fun list(): List<User> =
        database
            .from(UsersTable)
            .joinReferencesAndSelect()
            .where { UsersTable.type eq 1 }
          //  .orderBy(BusinessmanTable.creationdate.desc())
            .mapNotNull { row -> UsersTable.createEntity(row) }

}