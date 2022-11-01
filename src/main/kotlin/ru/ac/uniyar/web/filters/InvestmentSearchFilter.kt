package ru.ac.uniyar.web.filters

import org.http4k.lens.Query
import org.http4k.lens.localDate
import org.http4k.lens.string
import java.time.LocalDate


data class InvestmentFilterParams(
    val startDateL: LocalDate?,
    val startDateR: LocalDate?
)

class InvestmentSearchFilter {
    companion object {
        val startDateLField = Query.localDate().optional("startDateL")
        val startDateRField = Query.localDate().optional("startDateR")
    }
}
