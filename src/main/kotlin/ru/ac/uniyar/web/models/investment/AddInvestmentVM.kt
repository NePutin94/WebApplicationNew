package ru.ac.uniyar.web.models.investment

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel

data class AddInvestmentVM(val error : String =  String(), val form : WebForm = WebForm()) : ViewModel
