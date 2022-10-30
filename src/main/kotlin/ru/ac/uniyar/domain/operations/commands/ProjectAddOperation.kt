package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.support.mysql.insertOrUpdate
import ru.ac.uniyar.Project
import ru.ac.uniyar.domain.database.ProjectTable

class ProjectAddOperation(private val database: Database) {
    fun add(project: Project): Any {
        val id = database.insertAndGenerateKey(ProjectTable) {
            set(it.name, project.name)
            set(it.startdate, project.startDate)
            set(it.creationdate, project.creationDate)
            set(it.enddate, project.endDate)
            set(it.fundsize, project.fundSize)
            set(it.description, project.description)
            set(it.businessman, project.businessman.id)
        }
        return id
    }
}