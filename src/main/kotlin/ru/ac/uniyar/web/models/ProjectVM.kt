package ru.ac.uniyar.web.models

import org.http4k.core.Body
import org.http4k.lens.*
import org.http4k.template.ViewModel
import ru.ac.uniyar.Project

data class ProjectVM(val project: Project) : ViewModel {
    companion object {
        val nameField = FormField.nonEmptyString().required("name")
        val businessmanField = FormField.nonEmptyString().required("businessman")
        val descField = FormField.nonEmptyString().required("description")
        val fundSizeField = FormField.int().required("fundSize")
        val startDateField = FormField.dateTime().required("startDate")
        val endDateField = FormField.dateTime().required("endDate")
        val projectLens = Body.webForm(
            Validator.Feedback,
            nameField,
            businessmanField,
            descField,
            fundSizeField,
            startDateField,
            endDateField
        ).toLens()
    }
}