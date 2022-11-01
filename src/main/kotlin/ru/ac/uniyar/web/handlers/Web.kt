package ru.ac.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.*
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.web.handlers.businessman.BusinessmanRoutes
import ru.ac.uniyar.web.handlers.investment.InvestmentRoutes
import ru.ac.uniyar.web.handlers.project.ProjectRoutes
import ru.ac.uniyar.web.models.*

fun mainViewHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = MainVM("Совместное финансирование")
    Response(Status.OK).with(htmlView of viewModel)
}

fun descViewHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = ProjectDescVM("")
    Response(Status.OK).with(htmlView of viewModel)
}

val Web: (operationHolder: OperationHolder, BiDiBodyLens<ViewModel>) -> RoutingHttpHandler =
    { operationHolder, htmlView ->
        routes(
            "/" bind Method.GET to mainViewHandler(htmlView),
            "/help" bind Method.GET to descViewHandler(htmlView),
            ProjectRoutes(operationHolder, htmlView),
            InvestmentRoutes(operationHolder, htmlView),
            BusinessmanRoutes(operationHolder, htmlView)
        )
    }