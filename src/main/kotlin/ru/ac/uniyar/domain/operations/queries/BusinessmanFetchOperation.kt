package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.database.BusinessmanTable

class BusinessmanFetchOperation(
    private val database: Database,
) {
    fun fetch(businessmanId: Int): Businessman? =
        database.from(BusinessmanTable).select().where { BusinessmanTable.id eq businessmanId }
            .mapNotNull { row -> BusinessmanTable.createEntity(row) }.firstOrNull()

    fun fetchByName(name: String): Businessman? =
        database.from(BusinessmanTable).select().where { BusinessmanTable.name eq name }
            .mapNotNull { row -> BusinessmanTable.createEntity(row) }.firstOrNull()
}