package ru.ac.uniyar.domain.database.filters

import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.database.ProjectTable
import java.time.LocalDateTime

fun makeProjectFilterExpr(
    businessmanId: Int?,
    fundSize: Int,
    fundSizeOp: String?,
    startDateL: LocalDateTime?,
    startDateR: LocalDateTime?,
    endDateL: LocalDateTime?,
    endDateR: LocalDateTime?,
    projectIsColsed: Boolean
): ColumnDeclaring<Boolean> {
    var filterExpr: ColumnDeclaring<Boolean> = ProjectTable.id eq 0 or true //always true
    if (businessmanId != null) {
        filterExpr = filterExpr.and(ProjectTable.businessman eq businessmanId)
    }
    if (fundSize != 0) {
        filterExpr = when (fundSizeOp!!) {
            "<" -> {
                filterExpr.and(ProjectTable.fundsize less fundSize)
            }

            ">" -> {
                filterExpr.and(ProjectTable.fundsize greater fundSize)
            }

            else -> {
                filterExpr.and(ProjectTable.fundsize eq fundSize)
            }
        }
    }
    if (startDateL != null && startDateR != null)
        filterExpr =
            filterExpr.and(ProjectTable.startdate between startDateL..startDateR)
    if (endDateL != null && endDateR != null)
        filterExpr =
            filterExpr.and(ProjectTable.enddate between endDateL..endDateR)
    if (projectIsColsed) {
        val time = LocalDateTime.now()
        filterExpr =
            filterExpr.and(ProjectTable.enddate lessEq time)
    }
    return filterExpr
}