package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class ProjectAddInvestmentVM(val error : String =  String(), val form : WebForm = WebForm()) : ViewModel
