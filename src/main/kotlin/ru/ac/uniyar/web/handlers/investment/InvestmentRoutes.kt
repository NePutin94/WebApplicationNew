package ru.ac.uniyar.web.handlers.investment

import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.lens.RequestContextLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.permissionFilter
import ru.ac.uniyar.web.handlers.businessman.makeInvestmentFormHandler
import ru.ac.uniyar.web.handlers.businessman.makeInvestmentHandler
import ru.ac.uniyar.web.permission.Permissions


val InvestmentRoutes: (operationHolder: OperationHolder, ContextAwareViewRender, premLens: RequestContextLens<Permissions>, RequestContextLens<UsetState?>) -> RoutingHttpHandler =
    { operationHolder, htmlView, permissionLens, contextLens ->
        routes(
            "/makeInvestment" bind Method.GET to permissionFilter(permissionLens, Permissions::canAddInvestment).then(
                makeInvestmentHandler(htmlView)
            ),
            "/makeInvestment" bind Method.POST to permissionFilter(permissionLens, Permissions::canAddInvestment).then(
                makeInvestmentFormHandler(
                    contextLens,
                    operationHolder.addProjectInvestOperation,
                    operationHolder.fetchProjectOperation,
                    htmlView
                )
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