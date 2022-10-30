package ru.ac.uniyar.domain.operations.commands

import org.ktorm.database.Database
import org.ktorm.dsl.insertAndGenerateKey
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.domain.database.BusinessmanTable

class BusinessmanAddOperation(private val database: Database) {
    fun add(businessman: Businessman): Int {
        val id = database.insertAndGenerateKey(BusinessmanTable) {
            set(it.name, businessman.name)
            set(it.creationdate, businessman.creationDate)
        }
        return id as Int
    }
}