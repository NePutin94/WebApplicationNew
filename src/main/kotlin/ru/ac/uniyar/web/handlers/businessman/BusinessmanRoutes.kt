package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.Method
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.OperationHolder

val BusinessmanRoutes: (operationHolder: OperationHolder, BiDiBodyLens<ViewModel>) -> RoutingHttpHandler =
    { operationHolder, htmlView ->
        routes(
            "/viewBusinessman" bind Method.GET to listBusinessmanViewHandler(
                operationHolder.businessmanListOperation,
                operationHolder.listProjectsOperation,
                htmlView
            ),
            "/viewBusinessman/{id}" bind Method.GET to detailedBusinessmanHandler(
                operationHolder.listProjectsOperation,
                operationHolder.businessmanFetchOperation,
                operationHolder.fetchInvestments,
                htmlView
            ),
            "/addBusinessman" bind Method.GET to addBusinessmanViewHandler(htmlView),
            "/addBusinessman" bind Method.POST to addBusinessmanFormHandler(htmlView, operationHolder.addBusinessman)
        )
    }