package ru.ac.uniyar.web

import org.http4k.cloudnative.env.Environment
import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.cookie.cookies
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
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.domain.operations.queries.UserFetchOperation
import ru.ac.uniyar.util.ContextAwarePebbleTemplates
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.models.ErrorMessageVM
import ru.ac.uniyar.web.handlers.Web

val errFilter: (ContextAwareViewRender) -> Filter = { htmlView ->
    Filter { next: HttpHandler ->
        { request: Request ->
            val response = next(request)
            if (response.status.successful) {
                response
            } else {
                response.with(htmlView(request) of ErrorMessageVM(request.uri.toString()))
            }
        }
    }
}

val appRoutes: (operationHolder: OperationHolder, ContextAwareViewRender, Environment, JwtTools, RequestContextLens<UsetState?>) -> RoutingHttpHandler =
    { operationHolder, htmlView, appEnv, jwt, contextLens ->
        routes(
            Web(operationHolder, htmlView, appEnv, jwt, contextLens),
            static(ResourceLoader.Classpath("public"))
        )
    }

fun startDbServer(): H2DatabaseManager {
    val databaseManager = H2DatabaseManager().initialize()
    println("Веб-интерфейс базы данных доступен по адресу http://localhost:${H2DatabaseManager.WEB_PORT}")
    println("Введите любую строку, чтобы завершить работу приложения")
    //if filling is used, it is better to clear the database first
    //databaseManager.dropAll()
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
                val value = jwt.check(f.value)
                val user_id = value?.subject
                val user = userFetch.fetch(user_id!!.toInt())!!
                next(
                    request.with(contextLens of UsetState(user.name, user.id.toString()))
                )
            } else {
                next(request.with(premLens of Permissions()))
            }
        }
    }

fun startWebServer(operationHolder: OperationHolder, appEnv: Environment, jwt: JwtTools) {
    val contexts = RequestContexts()
    //val currentWorkerLens: RequestContextLens<UsetState?> = RequestContextKey.optional(contexts, "user")
    val contextLens = RequestContextKey.optional<UsetState>(contexts)
    val permLens = RequestContextKey.required<Permissions>(contexts)
    val renderer = ContextAwarePebbleTemplates().HotReload("src/main/resources/")
    val htmlView = ContextAwareViewRender.withContentType(renderer, TEXT_HTML)
    //val printingApp: HttpHandler = errFilter(htmlView).then(appRoutes(operationHolder, htmlView, appEnv, jwt, contextLens))
    val htmlViewWithContext = htmlView
        .associateContextLens("context", contextLens)

    val server = ServerFilters.InitialiseRequestContext(contexts)
        .then(addStateFilter(permLens, contextLens, jwt, operationHolder.userFetch))
        .then(errFilter(htmlViewWithContext))
        .then(appRoutes(operationHolder, htmlViewWithContext, appEnv, jwt, contextLens))
        .asServer(Undertow(9000)).start()
    println("Server started on http://localhost:" + server.port())
}

data class UsetState(val name: String, val id: String) : ViewModel

val AuthorizedUserPerm = Permissions(true, true, true)
val BusinessmanPerm = Permissions(true, true, true, true, true, true, true, true)

data class Permissions(
    val canAddInvestment: Boolean = false,
    val canViewUserPage: Boolean = false,
    val canCreateProject: Boolean = false,
    val canViewUserProjects: Boolean = false,
    val canEditProject: Boolean = false,
    val canCloseProject: Boolean = false,
    val canDeleteProject: Boolean = false,
    val canViewInvestmentsOfProject: Boolean = false,
)

fun startApplication() {

    val jwtGen = JwtTools("sadaszx", "Test", 60000)
    val defaultEnv = Environment.defaults(
        webPortLens of 9000,
    )
    val appEnv = Environment.ENV overrides defaultEnv

    val databaseManager = startDbServer()
    val db = connectToDatabase()
    val opHolder = OperationHolder(db)

    //filling the database
    //fill(opHolder)

    startWebServer(opHolder, appEnv, jwtGen)

    readlnOrNull()
    //  databaseManager.stopServers()
}