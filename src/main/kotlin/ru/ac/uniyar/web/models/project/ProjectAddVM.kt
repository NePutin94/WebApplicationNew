package ru.ac.uniyar.web.models.project

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class ProjectAddVM(
    val businessmanNames: List<String>,
    val selectedBName: String = "None",
    val form: WebForm = WebForm(),
    val message : String? = null
) : ViewModel