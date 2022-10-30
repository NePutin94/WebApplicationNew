package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.Project
import ru.ac.uniyar.domain.database.ProjectTable

class ProjectListOperation(
    private val database: Database,
    private val pageSize: Int
) {
    fun listPagination(pageIndex: Int): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .limit(pageIndex, pageSize)
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun listPaginationFiltered(pageIndex: Int, filter: () -> ColumnDeclaring<Boolean>): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .where(filter)
            .orderBy(ProjectTable.enddate.desc())
            .limit(pageIndex * pageSize, pageSize)
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun list(): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun listByBusinessmanId(businessmanId: Int): List<Project> =
        database.from(ProjectTable).select().where { ProjectTable.businessman eq businessmanId }
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun getTotalCount(filter: () -> ColumnDeclaring<Boolean>) = database
        .from(ProjectTable)
        .joinReferencesAndSelect()
        .totalRecords

    fun getPagesCount(filter: () -> ColumnDeclaring<Boolean>) = getTotalCount(filter) / pageSize
}


