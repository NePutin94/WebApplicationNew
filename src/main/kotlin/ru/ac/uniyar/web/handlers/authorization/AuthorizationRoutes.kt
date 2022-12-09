package ru.ac.uniyar.web.handlers.authorization

import org.http4k.cloudnative.env.Environment
import org.http4k.core.Method
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.RequestContextLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.authorization.JwtTools
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.UsetState

val AuthorizationRoutes: (operationHolder: OperationHolder, ContextAwareViewRender, Environment, JwtTools, RequestContextLens<UsetState?>) -> RoutingHttpHandler =
    { operationHolder, htmlView, env, jwt, contextLens ->
        routes(
            "/registration" bind Method.GET to RegistrationHandler(
                contextLens,
                htmlView
            ),
            "/registration" bind Method.POST to RegistrationFormHandler(
                contextLens,
                jwt,
                env,
                operationHolder.addUser,
                operationHolder.usersList,
                htmlView
            )
        )
    }