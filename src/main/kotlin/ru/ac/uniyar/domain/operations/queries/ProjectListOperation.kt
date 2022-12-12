package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.database.ProjectTable
import java.math.RoundingMode

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
            .orderBy(ProjectTable.startdate.desc()) //eq database.projects.sortedBy({it.enddate.desc()}, {it.startdate.desc()}).toList()
            .orderBy(ProjectTable.enddate.desc())
            .limit(pageIndex * pageSize, pageSize)
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun listPaginationFiltered(pageIndex: Int): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .orderBy(ProjectTable.startdate.desc()) //eq database.projects.sortedBy({it.enddate.desc()}, {it.startdate.desc()}).toList()
            .orderBy(ProjectTable.enddate.desc())
            .limit(pageIndex * pageSize, pageSize)
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun list(): List<Project> =
        database
            .from(ProjectTable)
            .joinReferencesAndSelect()
            .orderBy(ProjectTable.startdate.desc())
            .orderBy(ProjectTable.enddate.desc())
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun listProjectNames(): List<String> =
        database
            .from(ProjectTable)
            .select(ProjectTable.name)
            .mapNotNull { row -> row[ProjectTable.name]!! }

    fun listByBusinessmanId(userId: Int): List<Project> =
        database.from(ProjectTable).select().where { ProjectTable.user eq userId }
            .mapNotNull { row -> ProjectTable.createEntity(row) }

    fun getTotalCount(filter: () -> ColumnDeclaring<Boolean>) = database
        .from(ProjectTable)
        .joinReferencesAndSelect()
        .where(filter)
        .totalRecords

    fun getTotalCount() = database
        .from(ProjectTable)
        .joinReferencesAndSelect()
        .totalRecords

    fun getPagesCount(filter: () -> ColumnDeclaring<Boolean>): Int {
        val res = getTotalCount(filter).toDouble() / pageSize.toDouble()
        return res.toBigDecimal().setScale(0, RoundingMode.CEILING).toInt()
    }

    fun getPagesCount(): Int {
        val res = getTotalCount().toDouble() / pageSize.toDouble()
        return res.toBigDecimal().setScale(0, RoundingMode.CEILING).toInt()
    }
}


