package ru.ac.uniyar.web.handlers.project

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
import ru.ac.uniyar.web.permission.Permissions


val ProjectRoutes: (operationHolder: OperationHolder, ContextAwareViewRender, premLens: RequestContextLens<Permissions>, context: RequestContextLens<UsetState?>) -> RoutingHttpHandler =
    { operationHolder, htmlView, permissionLens, context ->
        routes(
            "/viewProjects" bind Method.GET to listProjectsViewHandler(
                operationHolder.listProjectsOperation,
                operationHolder.usersList,
                htmlView
            ),
            "/addProject" bind Method.GET to permissionFilter(permissionLens, Permissions::canCreateProject).then(
                addProjectViewHandler(context, htmlView)
            ),
            "/viewProjects/{id}" bind Method.GET to detailedProjectViewHandler(
                operationHolder.fetchProjectOperation,
                operationHolder.listInvestmentOperation,
                htmlView
            ),
            "/addProject" bind Method.POST to permissionFilter(permissionLens, Permissions::canCreateProject).then(
                addProjectFormHandler(
                    context,
                    operationHolder.userUpdate,
                    operationHolder.addProjectOperation,
                    htmlView
                )
            ),
        )
    }