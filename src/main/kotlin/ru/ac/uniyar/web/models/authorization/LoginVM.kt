package ru.ac.uniyar.web.models.authorization

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class LoginVM(val form: WebForm = WebForm(), val userFound: Boolean = true) : ViewModel