package ru.ac.uniyar.domain.database

import org.ktorm.schema.*
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project
import ru.ac.uniyar.Investment

object ProjectTable : Table<Project>("PROJECTS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val name = varchar("NAME").bindTo { it.name }
    val businessman = int("BUSINESSMAN_ID").references(BusinessmanTable) { it.businessman }
    val description = varchar("DESCRIPTION").bindTo { it.description }
    val fundsize = int("FUNDSIZE").bindTo { it.fundSize }
    val startdate = datetime("STARTDATE").bindTo { it.startDate }
    val enddate = datetime("ENDDATE").bindTo { it.endDate }
    val creationdate = datetime("CREATIONDATE").bindTo { it.creationDate }
}

object BusinessmanTable : Table<Businessman>("BUSINESSMANS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val name = varchar("NAME").bindTo { it.name }
    val creationdate = datetime("CREATIONDATE").bindTo { it.creationDate }
}

object InvestmentTable : Table<Investment>("PROJECTINVESTMENTS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val invName = varchar("NAME").bindTo { it.invName }
    val creationdate = datetime("CREATIONDATE").bindTo { it.creationDate }
    val project = int("PROJECT_ID").references(ProjectTable) { it.project }
    val feedback = varchar("FEEDBACK").bindTo { it.feedback }
    val amount = int("AMOUNT").bindTo { it.amount }
}

data class Test(val id: Int, val name: String?, val amount: Int)

object TestTable : Table<Nothing>("TEST") {
    val id = int("ID").primaryKey()
    val name = varchar("NAME")
    val amount = int("AMOUNT")
}