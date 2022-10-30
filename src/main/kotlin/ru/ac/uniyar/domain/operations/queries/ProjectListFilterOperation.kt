package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.expression.BinaryExpression
import org.ktorm.expression.UnaryExpression
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.Project
import ru.ac.uniyar.domain.database.ProjectTable

class ProjectListFilterOperation(
    private val database: Database,
) {
    fun listPaginationFiltered(filter: () -> ColumnDeclaring<Boolean>): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .where(filter)
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun list(): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .mapNotNull { row -> ProjectTable.createEntity(row) }

}