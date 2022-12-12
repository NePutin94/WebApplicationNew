package ru.ac.uniyar.web

import org.http4k.cloudnative.env.Environment
import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.cookies
import org.http4k.core.cookie.removeCookie
import org.http4k.filter.ServerFilters
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.RequestContextKey
import org.http4k.lens.RequestContextLens
import org.http4k.routing.ResourceLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel
import ru.ac.uniyar.AppConfig.Companion.webPortLens
import ru.ac.uniyar.domain.authorization.JwtTools
import ru.ac.uniyar.domain.database.*
import ru.ac.uniyar.domain.entities.TypesEnum
import ru.ac.uniyar.domain.entities.User
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.domain.operations.queries.UserFetchOperation
import ru.ac.uniyar.util.ContextAwarePebbleTemplates
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.models.ErrorMessageVM
import ru.ac.uniyar.web.handlers.Web
import ru.ac.uniyar.web.models.businessman.BusinessmanAddVM
import ru.ac.uniyar.web.permission.*
import java.time.LocalDateTime
import java.time.ZoneId

val errFilter: (ContextAwareViewRender) -> Filter = { htmlView ->
    Filter { next: HttpHandler ->
        { request: Request ->
            val response = next(request)
            if (response.status.successful) {
                response
            } else if (response.status.code == Status.FORBIDDEN.code) {
                response.with(htmlView(request) of ErrorMessageVM("Доступ запрещен", request.uri.toString()))
            } else {
                response.with(htmlView(request) of ErrorMessageVM("Ошибка", request.uri.toString()))
            }
        }
    }
}

val appRoutes: (operationHolder: OperationHolder, ContextAwareViewRender, Environment, JwtTools, RequestContextLens<UsetState?>, premLens: RequestContextLens<Permissions>) -> RoutingHttpHandler =
    { operationHolder, htmlView, appEnv, jwt, contextLens, permissions ->
        routes(
            Web(operationHolder, htmlView, appEnv, jwt, contextLens, permissions),
            static(ResourceLoader.Classpath("public"))
        )
    }

fun startDbServer(): H2DatabaseManager {
    val databaseManager = H2DatabaseManager().initialize()
    println("Веб-интерфейс базы данных доступен по адресу http://localhost:${H2DatabaseManager.WEB_PORT}")
    println("Введите любую строку, чтобы завершить работу приложения")
    //if filling is used, it is better to clear the database first
    databaseManager.dropAll()
    performMigrations()
    return databaseManager
}

fun addStateFilter(
    premLens: RequestContextLens<Permissions>,
    contextLens: RequestContextLens<UsetState?>,
    jwt: JwtTools,
    userFetch: UserFetchOperation
) =
    Filter { next ->
        { request ->
            val f = request.cookies().find { cookie -> cookie.name == "auth_token" }
            if (f != null) {
                if (f.value.isEmpty()) //error remove cookie
                {
                    val date = LocalDateTime.of(1970, 1, 1, 0, 0)
                    next(
                        request.with(premLens of Guest).cookie(
                            Cookie(
                                "auth_token", "", -1, date.atZone(
                                    ZoneId.systemDefault()
                                ).toInstant()
                            )
                        )
                    )
                } else {
                    try {
                        val value = jwt.check(f.value)
                        val userId = value?.subject
                        val currContext = contextLens(request)
                        val user: User =
                            if (currContext == null || currContext.user.id != userId!!.toInt()) {
                                userFetch.fetch(userId!!.toInt())!! //throws an exception
                            } else
                                currContext.user
                        val perm = setPerm(TypesEnum.fromInt(user.type.id))
                        next(
                            request.with(contextLens of UsetState(user.id.toString(), user))
                                .with(premLens of perm)
                        )
                    } catch (ex: Exception) //token has expired or token is invalid -> delete the cookie from the browser
                    {
                        val date = LocalDateTime.of(1970, 1, 1, 0, 0)
                        next(
                            request.with(premLens of Guest)
                                .cookie(
                                    Cookie(
                                        "auth_token", "", -1, date.atZone(
                                            ZoneId.systemDefault()
                                        ).toInstant()
                                    )
                                )
                        )
                    }
                }
            } else {
                next(request.with(premLens of Guest))
            }
        }
    }

fun startWebServer(operationHolder: OperationHolder, appEnv: Environment, jwt: JwtTools) {
    val contexts = RequestContexts()
    val contextLens = RequestContextKey.optional<UsetState>(contexts)
    val permLens = RequestContextKey.required<Permissions>(contexts)
    val renderer = ContextAwarePebbleTemplates().HotReload("src/main/resources/")
    val htmlView = ContextAwareViewRender.withContentType(renderer, TEXT_HTML)
    val htmlViewWithContext = htmlView
        .associateContextLens("context", contextLens)

    val server = ServerFilters.InitialiseRequestContext(contexts)
        .then(addStateFilter(permLens, contextLens, jwt, operationHolder.userFetch))
        .then(errFilter(htmlViewWithContext))
        .then(appRoutes(operationHolder, htmlViewWithContext, appEnv, jwt, contextLens, permLens))
        .asServer(Undertow(appEnv["web.port"]!!.toInt())).start()
    println("Server started on http://localhost:" + server.port())
}


fun startApplication() {

    val jwtGen = JwtTools("bvn%yW5*bd", "Co-financing", 80000)
    val defaultEnv = Environment.defaults(
        webPortLens of 9000,
    )
    val appEnv = Environment.ENV overrides defaultEnv

    val databaseManager = startDbServer()
    val db = connectToDatabase()
    val opHolder = OperationHolder(db)

    //filling the database
    fill(opHolder, appEnv)

    startWebServer(opHolder, appEnv, jwtGen)

    readlnOrNull()
    databaseManager.stopServers()
}