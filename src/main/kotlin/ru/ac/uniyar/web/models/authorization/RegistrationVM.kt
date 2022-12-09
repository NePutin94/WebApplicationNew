package ru.ac.uniyar.web.models.authorization

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class RegistrationVM(val form: WebForm = WebForm()): ViewModel