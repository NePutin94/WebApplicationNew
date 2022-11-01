package ru.ac.uniyar.web.models.investment

import org.http4k.template.ViewModel
import ru.ac.uniyar.Investment
import ru.ac.uniyar.Project
import ru.ac.uniyar.web.filters.InvestmentFilterParams
import ru.ac.uniyar.web.filters.ProjectFilterParams

data class InvestmentViewVM(
    val investments: Iterable<Investment>,
    val pageIndex: Int,
    val pageCount: Int,
    val investmentFilterParams: InvestmentFilterParams,
    val searchUriParams : String?,
    val uri4BackUri : String?
    ) : ViewModel
