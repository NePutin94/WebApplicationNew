package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import org.http4k.lens.WebForm
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.operations.commands.InvestmentAddOperation
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.InvestmentFilter
import ru.ac.uniyar.web.models.investment.InvestmentAddVM
import java.time.LocalDateTime

fun makeInvestmentHandler(htmlView: ContextAwareViewRender): HttpHandler = { request ->
    val viewModel = InvestmentAddVM()
    Response(Status.OK).with(htmlView(request) of viewModel)
}

fun makeInvestmentFormHandler(
    context: RequestContextLens<UsetState?>,
    addProjectInvestOperation: InvestmentAddOperation,
    fetchProjectOperation: FetchProjectOperation,
    htmlView: ContextAwareViewRender
): HttpHandler = { request ->
    val validForm = InvestmentFilter.investLens(request)

    val returnResponse = { name: String?, fb: String?, pr: Project, am: Int ->
        val id = addProjectInvestOperation.add(Investment {
            project = pr
            invName = name
            feedback = fb
            amount = am
            creationDate = LocalDateTime.now()
        })
        Response(Status.FOUND).header("Location", "/viewInvestment/${id}")
    }
    val returnErr = { err: String, webForm: WebForm ->
        val viewModel = InvestmentAddVM(err, webForm)
        Response(Status.OK).with(htmlView(request) of viewModel)
    }
    if (validForm.errors.isEmpty()) {
        val fname = InvestmentFilter.nameField(validForm)?.trim()
        val fprojectName = InvestmentFilter.projectField(validForm).trim()
        val ffeedback = InvestmentFilter.feedbackField(validForm)
        val famount = InvestmentFilter.amountField(validForm)
        val useProfile = InvestmentFilter.useProfileDataField(validForm) != null

        val findProject = fetchProjectOperation.fetchByName(fprojectName)
        if (findProject == null) {
            returnErr("Такого проекта не существует!", validForm)
        } else if (findProject.endDate <= LocalDateTime.now()) {
            returnErr("Проект уже закрыт!", validForm)
        } else {
            if (useProfile) {
                val reqContext = context(request)!!
                returnResponse(reqContext.user.name, reqContext.user.feedback, findProject, famount)
            } else
                returnResponse(fname, ffeedback, findProject, famount)
        }
    } else {
        returnErr("", validForm);
    }
}
