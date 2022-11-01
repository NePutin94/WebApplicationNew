package ru.ac.uniyar.web.filters

import org.http4k.core.Body
import org.http4k.lens.*

class BusinessmanFilters {
    companion object {
        val nameField = FormField.nonEmptyString().required("name")
        val businessmanLens = Body.webForm(
            Validator.Feedback,
            nameField
        ).toLens()
    }
}