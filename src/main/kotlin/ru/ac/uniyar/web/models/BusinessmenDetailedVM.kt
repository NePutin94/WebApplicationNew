package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project

data class BusinessmenDetailedVM(
    val businessman: Businessman,
    val businessmanProjectList: Iterable<Project>,
    val projectsInvestSum: Map<String, Int>,
    val successProjects: Map<String, Boolean>
) : ViewModel