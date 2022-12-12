package ru.ac.uniyar.web.models.user

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.User
import java.time.LocalDate

data class UserViewVM(
    val user: User,
    val investments: Map<Project, List<Investment>> = mapOf(),
    val projectsInvestSum: Map<Int, Int> = mapOf(),
    val projects: List<Project> = listOf(),
    val endDateL: LocalDate?,
    val endDateR: LocalDate?,
    val projectIsClosed : Boolean?
) : ViewModel
