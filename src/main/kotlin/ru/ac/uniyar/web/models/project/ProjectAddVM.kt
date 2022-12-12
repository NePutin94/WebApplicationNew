package ru.ac.uniyar.web.models.project

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class ProjectAddVM(
    val userName: String,
    val form: WebForm = WebForm(),
    val message : String? = null
) : ViewModel