package ru.ac.uniyar.web

import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.ResourceLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel
import org.ktorm.dsl.from
import org.ktorm.dsl.joinReferencesAndSelect
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project
import ru.ac.uniyar.ProjectInvestment
import ru.ac.uniyar.domain.database.*
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.web.models.ErrorMessageVM
import ru.ac.uniyar.web.handlers.Api
import ru.ac.uniyar.web.handlers.Web
import java.time.LocalDate
import java.time.LocalDateTime

val errFilter: (BiDiBodyLens<ViewModel>) -> Filter = { htmlView ->
    Filter { next: HttpHandler ->
        { request: Request ->
            val response = next(request)
            if (response.status.successful) {
                response
            } else {
                response.with(htmlView of ErrorMessageVM(request.uri.toString()))
            }
        }
    }
}

val appRoutes: (operationHolder: OperationHolder, BiDiBodyLens<ViewModel>) -> RoutingHttpHandler =
    { operationHolder, htmlView ->
        routes(
            Web(operationHolder, htmlView),
            Api(operationHolder, htmlView),
            static(ResourceLoader.Classpath("public"))
        )
    }

fun startDbServer(): H2DatabaseManager {
    val databaseManager = H2DatabaseManager().initialize()
    println("Веб-интерфейс базы данных доступен по адресу http://localhost:${H2DatabaseManager.WEB_PORT}")
    println("Введите любую строку, чтобы завершить работу приложения")
    databaseManager.dropAll()
    performMigrations()
    return databaseManager
}

fun startWebServer(operationHolder: OperationHolder) {
    val renderer = PebbleTemplates().HotReload("src/main/resources/")
    val htmlView = Body.viewModel(renderer, TEXT_HTML).toLens()
    val printingApp: HttpHandler = errFilter(htmlView).then(appRoutes(operationHolder, htmlView))
    val server = printingApp.asServer(Undertow(9000)).start()
    println("Server started on http://localhost:" + server.port())
}

fun startApplication() {

    val databaseManager = startDbServer()
    val db = connectToDatabase()
    val opHolder = OperationHolder(db)

    fill(opHolder)

    startWebServer(opHolder)

    readlnOrNull()
    databaseManager.stopServers()
}