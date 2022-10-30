package ru.ac.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.ViewModel
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project
import ru.ac.uniyar.ProjectInvestment
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.domain.operations.commands.BusinessmanAddOperation
import ru.ac.uniyar.domain.operations.commands.ProjectAddOperation
import ru.ac.uniyar.domain.operations.commands.ProjectInvestmentAddOperation
import ru.ac.uniyar.domain.operations.queries.FetchBusinessmanOperation
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.web.filters.InvestmentFilter
import ru.ac.uniyar.web.models.ProjectAddInvestmentVM
import ru.ac.uniyar.web.models.ProjectAddVM
import ru.ac.uniyar.web.models.ProjectVM
import java.time.LocalDateTime

fun projectFormHandler(
    projectAddOperation: ProjectAddOperation,
    businessmanAddOperation: BusinessmanAddOperation,
    fetchBusinessmanOperation: FetchBusinessmanOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler = { request ->
    val validForm = ProjectVM.projectLens(request)
    if (validForm.errors.isEmpty()) {
        val _name = ProjectVM.nameField(validForm).trim()
        val _businessman = ProjectVM.businessmanField(validForm).trim()
        val _desc = ProjectVM.descField(validForm).trim()
        val _fundSize = ProjectVM.fundSizeField(validForm)
        val _startDate = ProjectVM.startDateField(validForm)
        val _endDate = ProjectVM.endDateField(validForm)
        val _creationDate = LocalDateTime.now().withNano(0).withSecond(0)

        val try_find = fetchBusinessmanOperation.fetchByName(_businessman)
        var b_id = -1
        if (try_find == null)
            b_id = businessmanAddOperation.add(Businessman {
                name = _businessman
                creationDate = LocalDateTime.now()
            })
        else
            b_id = try_find.id

        val id = projectAddOperation.add(Project {
            creationDate = _creationDate
            name = _name
            businessman.id = b_id
            description = _desc
            fundSize = _fundSize
            startDate = _startDate
            endDate = _endDate
        })
        Response(Status.FOUND).header("Location", "/viewProjects/$id")
    } else {
        val viewModel = ProjectAddVM(validForm)
        Response(Status.OK).with(htmlView of viewModel)
    }
}

fun investmentFormHandler(
    addProjectInvestOperation: ProjectInvestmentAddOperation,
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
            addProjectInvestOperation.add(ProjectInvestment {
                project = try_find
                invName = _name
                feedback = _feedback
                amount = _amount
                creationDate = LocalDateTime.now()
            })
            Response(Status.FOUND).header("Location", "/viewProjects")
        } else {
            val viewModel = ProjectAddInvestmentVM("Такого проекта не существует",validForm)
            Response(Status.OK).with(htmlView of viewModel)
        }
    } else {
        val viewModel = ProjectAddInvestmentVM("", validForm)
        Response(Status.OK).with(htmlView of viewModel)
    }
}

val Api: (operationHandler: OperationHolder, BiDiBodyLens<ViewModel>) -> RoutingHttpHandler =
    { operationHandler, htmlView ->
        routes(
            "/addProject" bind Method.POST to projectFormHandler(
                operationHandler.addProjectOperation,
                operationHandler.addBusinessman,
                operationHandler.fetchBusinessmanOperation,
                htmlView
            ),
            "/makeInvestment" bind Method.POST to investmentFormHandler(
                operationHandler.addProjectInvestOperation,
                operationHandler.fetchProjectOperation,
                htmlView
            )
        )
    }