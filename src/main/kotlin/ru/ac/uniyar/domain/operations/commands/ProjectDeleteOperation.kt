package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import ru.ac.uniyar.domain.database.ProjectTable

class ProjectDeleteOperation(private val database: Database) {
    fun delete(projectId : Int): Any {
        val id = database.delete(ProjectTable) {
          it.id eq projectId
        }
        return id
    }
}