package ru.ac.uniyar.web.handlers.investment

import org.http4k.core.Method
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.handlers.businessman.makeInvestmentFormHandler
import ru.ac.uniyar.web.handlers.businessman.makeInvestmentHandler


val InvestmentRoutes: (operationHolder: OperationHolder, ContextAwareViewRender) -> RoutingHttpHandler =
    { operationHolder, htmlView ->
        routes(
            "/makeInvestment" bind Method.GET to makeInvestmentHandler(htmlView),
            "/makeInvestment" bind Method.POST to makeInvestmentFormHandler(
                operationHolder.addProjectInvestOperation,
                operationHolder.fetchProjectOperation,
                htmlView
            ),
            "/viewInvestment" bind Method.GET to listInvestmentViewHandler(
                operationHolder.listInvestmentOperation,
                htmlView
            ),
            "/viewInvestment/{id}" bind Method.GET to detailedInvestmentViewHandler(
                operationHolder.fetchInvestments,
                htmlView
            ),
        )
    }