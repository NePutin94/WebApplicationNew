package ru.ac.uniyar.web.models.businessman

import org.http4k.template.ViewModel
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project

data class BusinessmenDetailedVM(
    val businessman: Businessman,
    val backUri: String?,
    val businessmanProjectList: Iterable<Project>,
    val projectsInvestSum: Map<String, Int>,
    val successProjects: Map<String, Boolean>,
    val projectClosed: Map<String, Boolean>
) : ViewModel