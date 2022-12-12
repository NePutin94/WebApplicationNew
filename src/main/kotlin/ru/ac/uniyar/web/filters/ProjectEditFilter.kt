package ru.ac.uniyar.web.filters

import org.http4k.core.Body
import org.http4k.lens.*

class ProjectEditFilter {
    companion object {
        val nameField = FormField.string().optional("name")
        val descField = FormField.string().optional("description")
        val fundSizeField = FormField.int().optional("fundSize")
        val startDateField = FormField.dateTime().optional("startDate")
        val endDateField = FormField.dateTime().optional("endDate")
        val projectEditLens = Body.webForm(
            Validator.Feedback,
            nameField,
            descField,
            fundSizeField,
            startDateField,
            endDateField
        ).toLens()
    }
}
