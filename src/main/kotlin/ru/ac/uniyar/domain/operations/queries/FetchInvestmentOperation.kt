package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.Project
import ru.ac.uniyar.ProjectInvestment
import ru.ac.uniyar.domain.database.ProjectInvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable

class FetchInvestmentOperation(
    private val database: Database,
) {
    fun fetch(projectId: Int): ProjectInvestment? =
        database.from(ProjectInvestmentTable).joinReferencesAndSelect().where { ProjectInvestmentTable.id eq projectId }
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }.firstOrNull()

    fun fetchByName(name: String): List<ProjectInvestment> =
        database.from(ProjectInvestmentTable).joinReferencesAndSelect().where { ProjectInvestmentTable.invName eq name }
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }

    fun fetchFiltered(filter: () -> ColumnDeclaring<Boolean>): List<ProjectInvestment> =
        database
            .from(ProjectInvestmentTable)
            .joinReferencesAndSelect()
            .where(filter)
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }
}