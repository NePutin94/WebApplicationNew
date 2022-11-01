package ru.ac.uniyar.web.filters

import org.http4k.lens.Query
import org.http4k.lens.string

class BasicFilters {
    companion object{
        val backUriField = Query.string().optional("backUri")
    }
}
