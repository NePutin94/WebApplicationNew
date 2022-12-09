package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ru.ac.uniyar.domain.entities.Project
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
}