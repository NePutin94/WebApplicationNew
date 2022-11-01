package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.template.ViewModel
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.domain.operations.commands.BusinessmanAddOperation
import ru.ac.uniyar.web.filters.BusinessmanFilters
import ru.ac.uniyar.web.models.businessman.BusinessmanAddVM
import java.time.LocalDateTime

fun addBusinessmanViewHandler(
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler = { request ->
    val viewModel = BusinessmanAddVM()
    Response(Status.OK).with(htmlView of viewModel)
}

fun addBusinessmanFormHandler(
    htmlView: BiDiBodyLens<ViewModel>,
    businessmanAddOperation: BusinessmanAddOperation
): HttpHandler = { request ->
    val validForm = BusinessmanFilters.businessmanLens(request)
    if (validForm.errors.isEmpty()) {
        val fname = BusinessmanFilters.nameField(validForm).trim()
        val id = businessmanAddOperation.add(Businessman {
            creationDate = LocalDateTime.now()
            name = fname
        })
        Response(Status.FOUND).header("Location", "/viewBusinessman/${id}")
    } else {
        val viewModel = BusinessmanAddVM(validForm)
        Response(Status.OK).with(htmlView of viewModel)
    }
}