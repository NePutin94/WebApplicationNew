package ru.ac.uniyar.web.handlers

import org.http4k.cloudnative.env.Environment
import org.http4k.core.*
import org.http4k.core.cookie.removeCookie
import org.http4k.lens.RequestContextLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import ru.ac.uniyar.domain.authorization.JwtTools
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.handlers.authorization.AuthorizationRoutes
import ru.ac.uniyar.web.handlers.businessman.BusinessmanRoutes
import ru.ac.uniyar.web.handlers.investment.InvestmentRoutes
import ru.ac.uniyar.web.handlers.project.ProjectRoutes
import ru.ac.uniyar.web.handlers.uesr.UserRoutes
import ru.ac.uniyar.web.models.MainVM
import ru.ac.uniyar.web.models.ProjectDescVM
import ru.ac.uniyar.web.permission.Permissions

fun mainViewHandler(htmlView: ContextAwareViewRender): HttpHandler = { request ->
    val viewModel = MainVM("Совместное финансирование")
    Response(Status.OK).with(htmlView(request) of viewModel).removeCookie("auth_token")
}

fun descViewHandler(htmlView: ContextAwareViewRender): HttpHandler = { request ->
    val viewModel = ProjectDescVM("")
    Response(Status.OK).with(htmlView(request) of viewModel)
}

val Web: (operationHolder: OperationHolder, ContextAwareViewRender, Environment, JwtTools, RequestContextLens<UsetState?>, RequestContextLens<Permissions>) -> RoutingHttpHandler =
    { operationHolder, htmlView, appEnv, jwt, contextLens, permissions ->
        routes(
            "/" bind Method.GET to mainViewHandler(htmlView),
            "/help" bind Method.GET to descViewHandler(htmlView),
            ProjectRoutes(operationHolder, htmlView, permissions, contextLens),
            InvestmentRoutes(operationHolder, htmlView, permissions, contextLens),
            BusinessmanRoutes(operationHolder, htmlView, permissions),
            AuthorizationRoutes(operationHolder, htmlView, appEnv, jwt, contextLens),
            UserRoutes(operationHolder, htmlView, permissions, contextLens)
        )
    }