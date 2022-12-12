package ru.ac.uniyar.web.models.investment

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Investment

data class InvestmentDetailedVM(
    val investment: Investment
    ) : ViewModel
