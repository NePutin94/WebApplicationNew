package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.lens.RequestContextLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.filters.permissionFilter
import ru.ac.uniyar.web.permission.Permissions

val BusinessmanRoutes: (operationHolder: OperationHolder, ContextAwareViewRender,premLens: RequestContextLens<Permissions>) -> RoutingHttpHandler =
    { operationHolder, htmlView,permissionLens ->
        routes(
            "/viewBusinessman" bind Method.GET to listBusinessmanViewHandler(
                operationHolder.usersList,
                operationHolder.listProjectsOperation,
                htmlView
            ),
            "/viewBusinessman/{id}" bind Method.GET to permissionFilter(permissionLens, Permissions::canViewBusinessmans).then(detailedBusinessmanHandler(
                operationHolder.userInvs,
                operationHolder.listProjectsOperation,
                operationHolder.userFetch,
                htmlView
            )),
            //"/addBusinessman" bind Method.GET to addBusinessmanViewHandler(htmlView),
            //"/addBusinessman" bind Method.POST to addBusinessmanFormHandler(htmlView, operationHolder.addBusinessman)
        )
    }