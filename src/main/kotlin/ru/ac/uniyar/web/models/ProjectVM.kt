package ru.ac.uniyar.web.models

import org.http4k.core.Body
import org.http4k.lens.*
import org.http4k.template.ViewModel
import ru.ac.uniyar.Project

data class ProjectVM(val project: Project) : ViewModel {
}