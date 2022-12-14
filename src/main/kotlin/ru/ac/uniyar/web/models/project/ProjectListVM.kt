package ru.ac.uniyar.web.models.project

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.web.filters.ProjectFilterParams

data class ProjectListVM(
    val projects: Iterable<Project>,
    val pageIndex: Int,
    val pageCount: Int,
    val projectFilterParams: ProjectFilterParams
) : ViewModel