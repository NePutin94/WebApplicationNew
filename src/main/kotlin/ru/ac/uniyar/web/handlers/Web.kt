package ru.ac.uniyar.web.handlers

import org.http4k.cloudnative.env.Environment
import org.http4k.core.*
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.RequestContextLens
import org.http4k.routing.*
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.authorization.JwtTools
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.UsetState
import ru.ac.uniyar.web.handlers.authorization.AuthorizationRoutes
import ru.ac.uniyar.web.handlers.businessman.BusinessmanRoutes
import ru.ac.uniyar.web.handlers.investment.InvestmentRoutes
import ru.ac.uniyar.web.handlers.project.ProjectRoutes
import ru.ac.uniyar.web.models.*

fun mainViewHandler(htmlView: ContextAwareViewRender): HttpHandler = {
    request->
    val viewModel = MainVM("Совместное финансирование")
    Response(Status.OK).with(htmlView(request) of viewModel)
}

fun descViewHandler(htmlView: ContextAwareViewRender): HttpHandler = {
    request->
    val viewModel = ProjectDescVM("")
    Response(Status.OK).with(htmlView(request) of viewModel)
}

val Web: (operationHolder: OperationHolder, ContextAwareViewRender, Environment, JwtTools, RequestContextLens<UsetState?>) -> RoutingHttpHandler =
    { operationHolder, htmlView, appEnv, jwt,contextLens ->
        routes(
            "/" bind Method.GET to mainViewHandler(htmlView),
            "/help" bind Method.GET to descViewHandler(htmlView),
            ProjectRoutes(operationHolder, htmlView),
            InvestmentRoutes(operationHolder, htmlView),
            BusinessmanRoutes(operationHolder, htmlView),
            AuthorizationRoutes(operationHolder, htmlView, appEnv, jwt, contextLens)
        )
    }