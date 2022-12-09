package ru.ac.uniyar.web.handlers.project

import org.http4k.core.Method
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender


val ProjectRoutes: (operationHolder: OperationHolder, ContextAwareViewRender) -> RoutingHttpHandler =
    { operationHolder, htmlView ->
        routes(
            "/viewProjects" bind Method.GET to listProjectsViewHandler(
                operationHolder.listProjectsOperation,
                operationHolder.businessmanListOperation,
                htmlView
            ),
            "/addProject" bind Method.GET to addProjectViewHandler(htmlView, operationHolder.businessmanListOperation),
            "/viewProjects/{id}" bind Method.GET to detailedProjectViewHandler(
                operationHolder.fetchProjectOperation,
                operationHolder.listInvestmentOperation,
                htmlView
            ),
            "/addProject" bind Method.POST to addProjectFormHandler(
                operationHolder.addProjectOperation,
                operationHolder.businessmanListOperation,
                htmlView
            ),
        )
    }