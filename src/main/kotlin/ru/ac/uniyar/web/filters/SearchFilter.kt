package ru.ac.uniyar.web.filters

import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.lens.*
import java.time.LocalDate

data class ProjectFilterParams(
    val name: String?,
    val businessman: String?,
    val fundSize: Int?,
    val sign: String?,
    val startDateL: LocalDate?,
    val startDateR: LocalDate?,
    val uri : String
)

class SearchFilter {
    companion object {
        val nameField = Query.string().optional("name")
        val businessmanField = Query.string().optional("businessman")
        val fundSizeField = Query.int().defaulted("fundSize", 0)
        val sign = Query.string().optional("sign")
        val startDateLField = Query.localDate().optional("startDateL")
        val startDateRField = Query.localDate().optional("startDateR")
    }
}
