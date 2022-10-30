package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.ProjectInvestment
import ru.ac.uniyar.domain.database.ProjectInvestmentTable

class InvestmentListOperation(
    private val database: Database,
) {
    fun listFiltered(filter: () -> ColumnDeclaring<Boolean>): List<ProjectInvestment> =
        database
            .from(ProjectInvestmentTable)
            .joinReferencesAndSelect()
            .where(filter)
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }

    fun list(): List<ProjectInvestment> =
        database
            .from(ProjectInvestmentTable)
            .joinReferencesAndSelect()
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }

    fun listByProjectId(projectId: Int): List<ProjectInvestment> =
        database.from(ProjectInvestmentTable).joinReferencesAndSelect()
            .where { ProjectInvestmentTable.project eq projectId }
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }

    fun listByProjectId(projectId: Int, s: Int, e: Int): List<ProjectInvestment> =
        database.from(ProjectInvestmentTable).joinReferencesAndSelect()
            .where { ProjectInvestmentTable.project eq projectId }
            .limit(s, e)
            .mapNotNull { row -> ProjectInvestmentTable.createEntity(row) }
}