package ru.ac.uniyar.web.filters

import org.http4k.lens.*
import java.time.LocalDate

data class ProjectFilterParams(
    val fundSize: String?,
    val startDateL: LocalDate?,
    val startDateR: LocalDate?,
    val endDateL: LocalDate?,
    val endDateR: LocalDate?,
    val businessmanNames : List<String>,
    val selectedBName : String?,
    val projectIsClosed : Boolean?
)

class ProjectSearchFilter {
    companion object {
        val businessmanField = Query.string().defaulted("businessman", "Select all")
        val fundSizeField = Query.string().defaulted("fundSize", "")
        val startDateLField = Query.localDate().optional("startDateL")
        val startDateRField = Query.localDate().optional("startDateR")
        val endDateLField = Query.localDate().optional("endDateL")
        val endDateRField = Query.localDate().optional("endDateR")
        val projectIsClosed = Query.string().optional("projectIsClosed")
    }
}
