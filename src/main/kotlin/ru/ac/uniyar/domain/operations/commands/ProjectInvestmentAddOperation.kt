package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.insertAndGenerateKey
import ru.ac.uniyar.ProjectInvestment
import ru.ac.uniyar.domain.database.ProjectInvestmentTable

class ProjectInvestmentAddOperation(private val database: Database) {
    fun add(invest: ProjectInvestment): Any {
        val id = database.insertAndGenerateKey(ProjectInvestmentTable) {
            set(it.project, invest.project.id)
            set(it.amount, invest.amount)
            set(it.creationdate, invest.creationDate)
            set(it.feedback, invest.feedback)
            set(it.invName, invest.invName)
        }
        return id
    }
}