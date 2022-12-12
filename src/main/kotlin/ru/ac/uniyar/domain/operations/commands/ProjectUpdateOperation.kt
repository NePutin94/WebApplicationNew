package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.ProjectTable
import java.time.LocalDateTime


class ProjectUpdateOperation(private val database: Database) {
    fun update(
        projectId: Int,
        name: String,
        description: String,
        fundSize: Int,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Int {
        val id = database.update(ProjectTable) {
            set(it.name, name)
            set(it.description, description)
            set(it.fundsize, fundSize)
            set(it.startdate, startDate)
            set(it.enddate, endDate)
            where { it.id eq projectId }
        }
        return id
    }
    fun update(
        projectId: Int,
        endDate: LocalDateTime
    ): Int {
        val id = database.update(ProjectTable) {
            set(it.enddate, endDate)
            where { it.id eq projectId }
        }
        return id
    }
}