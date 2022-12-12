package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.database.InvestmentTable

class InvestmentFetchOperation(
    private val database: Database,
) {
    fun fetch(projectId: Int): Investment? =
        database.from(InvestmentTable).joinReferencesAndSelect().where { InvestmentTable.id eq projectId }
            .mapNotNull { row -> InvestmentTable.createEntity(row) }.firstOrNull()

//    fun fetchByName(name: String): List<Investment> =
//        database.from(InvestmentTable).joinReferencesAndSelect().where { InvestmentTable.user eq name }
//            .mapNotNull { row -> InvestmentTable.createEntity(row) }

    fun fetchFiltered(filter: () -> ColumnDeclaring<Boolean>): List<Investment> =
        database
            .from(InvestmentTable)
            .joinReferencesAndSelect()
            .where(filter)
            .mapNotNull { row -> InvestmentTable.createEntity(row) }
}