package ru.ac.uniyar.web.models.investment

import org.http4k.template.ViewModel
import ru.ac.uniyar.Investment

data class InvestmentDetailedVM(
    val investment: Investment,
    val backUri: String?
    ) : ViewModel
