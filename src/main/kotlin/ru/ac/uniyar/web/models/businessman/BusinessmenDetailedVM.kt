package ru.ac.uniyar.web.models.businessman

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.User

data class BusinessmenDetailedVM(
    val businessman: User,
    val backUri: String?,
    val businessmanProjectList: Iterable<Project>,
    val projectsInvestSum: Map<String, Int>,
    val successProjects: Map<String, Boolean>,
    val projectClosed: Map<String, Boolean>
) : ViewModel