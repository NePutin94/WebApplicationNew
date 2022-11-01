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
        val _name = InvestmentFilter.nameField(validForm).trim()
        val _projectName = InvestmentFilter.projectField(validForm).trim()
        val _feedback = InvestmentFilter.feedbackField(validForm)
        val _amount = InvestmentFilter.amountField(validForm)

        val try_find = fetchProjectOperation.fetchByName(_projectName)
        if (try_find != null) {
            addProjectInvestOperation.add(Investment {
                project = try_find
                invName = _name
                feedback = _feedback
                amount = _amount
                creationDate = LocalDateTime.now()
            })
            Response(Status.FOUND).header("Location", "/viewProjects")
        } else {
            val viewModel = AddInvestmentVM("Такого проекта не существует",validForm)
            Response(Status.OK).with(htmlView of viewModel)
        }
    } else {
        val viewModel = AddInvestmentVM("", validForm)
        Response(Status.OK).with(htmlView of viewModel)
    }
}
