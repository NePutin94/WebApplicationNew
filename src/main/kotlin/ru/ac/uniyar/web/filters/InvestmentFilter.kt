package ru.ac.uniyar.web.filters

import org.http4k.core.Body
import org.http4k.lens.*

class InvestmentFilter {
    companion object {
        val projectField = FormField.nonEmptyString().required("project")
        val nameField = FormField.string().optional("name")
        val amountField = FormField.int().required("amount")
        val feedbackField = FormField.string().optional("feedback")
        val useProfileDataField = FormField.string().optional("useProfileData")
        val investLens = Body.webForm(
            Validator.Feedback,
            projectField,
            nameField,
            amountField,
            feedbackField,
            useProfileDataField
        ).toLens()
    }
}