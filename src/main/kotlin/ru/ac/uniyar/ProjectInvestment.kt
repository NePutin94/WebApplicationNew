package ru.ac.uniyar

import org.ktorm.dsl.QueryRowSet
import org.ktorm.entity.Entity
import ru.ac.uniyar.domain.database.ProjectInvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable
import java.time.LocalDateTime

//data class ProjectInvestment(
//    val creationDate: LocalDateTime,
//    val project: Project,
//    val invName: String?,
//    val feedback: String?,
//    val amount: Int
//)

interface ProjectInvestment : Entity<ProjectInvestment> {
    val id: Int
    var creationDate: LocalDateTime
    var project: Project
    var invName: String?
    var feedback: String?
    var amount: Int

    companion object : Entity.Factory<ProjectInvestment>()
}