package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.Project
import ru.ac.uniyar.web.filters.ProjectFilterParams

data class ProjectListVM(
    val projects: Iterable<Project>,
    val pageIndex: Int,
    val pageCount: Int,
    val projectFilterParams: ProjectFilterParams
) : ViewModel