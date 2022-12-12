package ru.ac.uniyar.web.filters

import org.http4k.core.Body
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm

class LoginFilter {
    companion object {
        val nameField = FormField.nonEmptyString().required("name")
        val passField = FormField.nonEmptyString().required("password")
        val loginLens = Body.webForm(
            Validator.Feedback,
            nameField,
            passField
        ).toLens()
    }
}