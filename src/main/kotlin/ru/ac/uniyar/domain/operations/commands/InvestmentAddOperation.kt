package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.insertAndGenerateKey
import ru.ac.uniyar.Investment
import ru.ac.uniyar.domain.database.InvestmentTable

class InvestmentAddOperation(private val database: Database) {
    fun add(invest: Investment): Any {
        val id = database.insertAndGenerateKey(InvestmentTable) {
            set(it.project, invest.project.id)
            set(it.amount, invest.amount)
            set(it.creationdate, invest.creationDate)
            if (!invest.feedback.isNullOrEmpty())
                set(it.feedback, invest.feedback)
            if (!invest.invName.isNullOrEmpty())
                set(it.invName, invest.invName)
        }
        return id
    }
}