package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.domain.database.BusinessmanTable
import ru.ac.uniyar.domain.database.InvestmentTable

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

    fun list(): List<Businessman> =
        database
            .from(BusinessmanTable)
            .joinReferencesAndSelect()
            .orderBy(BusinessmanTable.creationdate.desc())
            .mapNotNull { row -> BusinessmanTable.createEntity(row) }

}