package ru.ac.uniyar.domain.database

import org.ktorm.schema.*
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.entities.User

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

object UsersTable : Table<User>("USERS") {
    val id = int("ID").primaryKey().bindTo { it.id }
    val creationdate = datetime("CREATIONDATE").bindTo { it.creationDate }
    val name = varchar("NAME").bindTo { it.name }
    val password = varchar("PASSWORD").bindTo { it.password }
    val type = int("TYPE").bindTo { it.type }
}