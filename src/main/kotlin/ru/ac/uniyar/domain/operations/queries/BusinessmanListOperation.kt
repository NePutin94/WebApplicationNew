package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.joinReferencesAndSelect
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.where
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.domain.database.BusinessmanTable

class BusinessmanListOperation(
    private val database: Database,
) {
    fun listFiltered(filter: () -> ColumnDeclaring<Boolean>): List<Businessman> =
        database
            .from(BusinessmanTable)
            .joinReferencesAndSelect()
            .where(filter)
            .mapNotNull { row -> BusinessmanTable.createEntity(row) }

    fun list(): List<Businessman> =
        database
            .from(BusinessmanTable)
            .joinReferencesAndSelect()
            .mapNotNull { row -> BusinessmanTable.createEntity(row) }

}