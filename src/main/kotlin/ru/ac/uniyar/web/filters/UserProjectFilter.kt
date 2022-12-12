package ru.ac.uniyar.web.filters

import org.http4k.lens.Query
import org.http4k.lens.localDate
import org.http4k.lens.string


class UserProjectFilter {
    companion object {
        val projectIsClosed = Query.string().optional("projectIsClosed")
        val endDateLField = Query.localDate().optional("endDateL")
        val endDateRField = Query.localDate().optional("endDateR")
    }
}
