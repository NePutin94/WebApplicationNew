package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.operations.commands.InvestmentAddOperation
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.filters.InvestmentFilter
import ru.ac.uniyar.web.models.investment.InvestmentAddVM
import java.time.LocalDateTime

fun makeInvestmentHandler(htmlView: ContextAwareViewRender): HttpHandler = {
    request->
    val viewModel = InvestmentAddVM()
    Response(Status.OK).with(htmlView(request) of viewModel)
}

fun makeInvestmentFormHandler(
    addProjectInvestOperation: InvestmentAddOperation,
    fetchProjectOperation: FetchProjectOperation,
    htmlView: ContextAwareViewRender
): HttpHandler = { request ->
    val validForm = InvestmentFilter.investLens(request)
    if (validForm.errors.isEmpty()) {
        val fname = InvestmentFilter.nameField(validForm)?.trim()
        val fprojectName = InvestmentFilter.projectField(validForm).trim()
        val ffeedback = InvestmentFilter.feedbackField(validForm)
        val famount = InvestmentFilter.amountField(validForm)

        val findProject = fetchProjectOperation.fetchByName(fprojectName)

        if (findProject == null) {
            val viewModel = InvestmentAddVM("Такого проекта не существует!", validForm)
            Response(Status.OK).with(htmlView(request) of viewModel)
        } else if (findProject.endDate <= LocalDateTime.now()) {
            val viewModel = InvestmentAddVM("Проект уже закрыт!", validForm)
            Response(Status.OK).with(htmlView(request) of viewModel)
        } else {
            val id = addProjectInvestOperation.add(Investment {
                project = findProject
                invName = fname
                feedback = ffeedback
                amount = famount
                creationDate = LocalDateTime.now()
            })
            Response(Status.FOUND).header("Location", "/viewInvestment/${id}")
        }
    } else {
        val viewModel = InvestmentAddVM("", validForm)
        Response(Status.OK).with(htmlView(request) of viewModel)
    }
}
