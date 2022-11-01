package ru.ac.uniyar.web.models.businessman

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class BusinessmanAddVM( val form: WebForm = WebForm()) : ViewModel {}