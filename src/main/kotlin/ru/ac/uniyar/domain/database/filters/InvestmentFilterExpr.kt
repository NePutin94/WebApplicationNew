package ru.ac.uniyar.domain.database.filters

import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.database.InvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable
import java.time.LocalDateTime

fun makeInvestmentFilterExpr(
    startDateL: LocalDateTime?,
    startDateR: LocalDateTime?
): ColumnDeclaring<Boolean> {
    var filterExpr: ColumnDeclaring<Boolean> = InvestmentTable.id eq 0 or true //always true
    if (startDateL != null && startDateR != null)
        filterExpr =
            filterExpr.and(InvestmentTable.creationdate between startDateL..startDateR)
    return filterExpr
}