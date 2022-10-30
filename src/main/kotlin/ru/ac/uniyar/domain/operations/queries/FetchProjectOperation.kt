package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project
import ru.ac.uniyar.domain.database.BusinessmanTable
import ru.ac.uniyar.domain.database.ProjectTable

class FetchProjectOperation(
    private val database: Database,
) {
    fun fetch(projectId: Int): Project? =
        database.from(ProjectTable).joinReferencesAndSelect().where { ProjectTable.id eq projectId }
            .mapNotNull { row -> ProjectTable.createEntity(row) }.firstOrNull()

    fun fetchByName(name: String): Project? =
        database.from(ProjectTable).select().where { ProjectTable.name eq name }
            .mapNotNull { row -> ProjectTable.createEntity(row) }.firstOrNull()
//        database
//            .from(ProjectTable)
//            .select(
//                ProjectTable.id, ProjectTable.creationdate,
//                ProjectTable.startdate, ProjectTable.enddate,
//                ProjectTable.name, ProjectTable.businessman,
//                ProjectTable.description, ProjectTable.fundsize
//            )
//            .where { ProjectTable.id eq projectId }
//            .mapNotNull(Project.fromResultSet)
//            .firstOrNull()
}