package ru.ac.uniyar.domain.database.filters

import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.database.InvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable
import java.time.LocalDateTime


fun projectInvestmentFilterExpr(
    projectIsClosed: Boolean?,
    endDateL: LocalDateTime?,
    endDateR: LocalDateTime?,
): ColumnDeclaring<Boolean> {
    var filterExpr: ColumnDeclaring<Boolean> = InvestmentTable.id eq 0 or true //always true

    if (endDateL != null && endDateR != null)
        filterExpr =
            filterExpr.and(ProjectTable.enddate between endDateL..endDateR)

    if (projectIsClosed != null && projectIsClosed) {
        val time = LocalDateTime.now()
        filterExpr =
            filterExpr.and(ProjectTable.enddate lessEq time)
    }

    return filterExpr
}