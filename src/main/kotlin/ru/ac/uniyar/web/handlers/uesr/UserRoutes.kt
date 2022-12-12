package ru.ac.uniyar.web.handlers.uesr

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

val UserRoutes: (operationHolder: OperationHolder, ContextAwareViewRender, premLens: RequestContextLens<Permissions>, context: RequestContextLens<UsetState?>) -> RoutingHttpHandler =
    { operationHolder, htmlView, permissionLens, context ->
        routes(
            "/viewUser" bind Method.GET to permissionFilter(permissionLens, Permissions::canViewUserPage).then(
                viewUserHandler(
                    operationHolder.listProjectsOperation,
                    operationHolder.userInvs,
                    context,
                    htmlView
                )
            ),
            "/userProjectInvestments/{id}" bind Method.GET to permissionFilter(
                permissionLens,
                Permissions::canViewInvestmentsOfProject
            ).then(
                UserProjectInvestHandler(
                    operationHolder.fetchProjectOperation,
                    operationHolder.listInvestmentOperation,
                    context,
                    htmlView
                )
            ),
            "/deleteProject/{id}" bind Method.GET to permissionFilter(
                permissionLens,
                Permissions::canDeleteProject
            ).then(
                UserDeleteProjectHandler(
                    operationHolder.fetchProjectOperation,
                    operationHolder.projectDelete,
                    context,
                    htmlView
                )
            ),
            "/closeProject/{id}" bind Method.GET to permissionFilter(
                permissionLens,
                Permissions::canCloseProject
            ).then(
                UserCloseProject(
                    operationHolder.fetchProjectOperation,
                    operationHolder.projectUpdate,
                    context,
                    htmlView
                )
            ),
            "/userViewProject/{id}" bind Method.GET to permissionFilter(
                permissionLens,
                Permissions::canViewInvestmentsOfProject
            ).then(
                UserViewProjectHandler(
                    operationHolder.fetchProjectOperation,
                    operationHolder.listInvestmentOperation,
                    context,
                    htmlView
                )
            ),
            "/userViewProject/{id}" bind Method.POST to permissionFilter(
                permissionLens,
                Permissions::canViewInvestmentsOfProject
            ).then(
                UserViewProjectFormHandler(
                    operationHolder.fetchProjectOperation,
                    operationHolder.projectUpdate
                )
            )
        )
    }