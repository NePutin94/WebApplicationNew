package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.template.ViewModel
import ru.ac.uniyar.Investment
import ru.ac.uniyar.domain.operations.commands.InvestmentAddOperation
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.web.filters.InvestmentFilter
import ru.ac.uniyar.web.models.investment.AddInvestmentVM
import java.time.LocalDateTime

fun makeInvestmentHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = AddInvestmentVM()
    Response(Status.OK).with(htmlView of viewModel)
}

fun makeInvestmentFormHandler(
    addProjectInvestOperation: InvestmentAddOperation,
    fetchProjectOperation: FetchProjectOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler = { request ->
    val validForm = InvestmentFilter.investLens(request)
    if (validForm.errors.isEmpty()) {
        val fname = InvestmentFilter.nameField(validForm)?.trim()
        val fprojectName = InvestmentFilter.projectField(validForm).trim()
        val ffeedback = InvestmentFilter.feedbackField(validForm)
        val famount = InvestmentFilter.amountField(validForm)

        val try_find = fetchProjectOperation.fetchByName(fprojectName)

        if (try_find == null) {
            val viewModel = AddInvestmentVM("Такого проекта не существует!", validForm)
            Response(Status.OK).with(htmlView of viewModel)
        } else if (try_find.endDate <= LocalDateTime.now()) {
            val viewModel = AddInvestmentVM("Проект уже закрыт!", validForm)
            Response(Status.OK).with(htmlView of viewModel)
        } else {
            val id = addProjectInvestOperation.add(Investment {
                project = try_find
                invName = fname
                feedback = ffeedback
                amount = famount
                creationDate = LocalDateTime.now()
            })
            Response(Status.FOUND).header("Location", "/viewInvestment/${id}")
        }
    } else {
        val viewModel = AddInvestmentVM("", validForm)
        Response(Status.OK).with(htmlView of viewModel)
    }
}
