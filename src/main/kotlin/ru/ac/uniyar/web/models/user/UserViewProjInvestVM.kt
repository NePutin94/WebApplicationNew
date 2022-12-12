package ru.ac.uniyar.web.models.user

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.User

data class UserViewProjInvestVM(
    val user: User,
    val project: Project,
    val investments: List<Investment> = listOf()
) : ViewModel