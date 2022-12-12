package ru.ac.uniyar.web.handlers.project

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.lens.string
import org.http4k.routing.path
import org.http4k.template.ViewModel
import org.ktorm.entity.toList
import ru.ac.uniyar.domain.database.filters.makeProjectFilterExpr
import ru.ac.uniyar.domain.entities.TypesEnum
import ru.ac.uniyar.domain.operations.queries.*
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.filters.BasicFilters
import ru.ac.uniyar.web.filters.ProjectFilterParams
import ru.ac.uniyar.web.filters.ProjectSearchFilter
import ru.ac.uniyar.web.filters.lensOrNull
import ru.ac.uniyar.web.models.project.ProjectDetailedVM
import ru.ac.uniyar.web.models.project.ProjectListVM
import java.sql.DriverManager
import java.time.Duration
import java.time.LocalDateTime

fun detailedProjectViewHandler(
    projectFetchOperation: FetchProjectOperation,
    listInvestmentOperation: InvestmentListOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val backUri = BasicFilters.backUriField(request)?.let { it.replace("*", "?").replace("~", "&") }
        val projectId = request.path("id")!!
        val findProject = projectFetchOperation.fetch(projectId.toInt())!!
        val investments = listInvestmentOperation.listByProjectId(projectId.toInt())
        val totalAmount = investments.sumOf { it.amount }

        val isClosed = findProject.endDate <= LocalDateTime.now()
        val remaining = Duration.between(LocalDateTime.now(), findProject.endDate).toDays()

        val deltaDate = Duration.between(findProject.startDate, findProject.endDate).toDays()
        val amountPerDay = (totalAmount / deltaDate).toInt()

        val successForecast =
            if ((findProject.fundSize - totalAmount) - remaining * amountPerDay <= 0) "Проект будет успешен" else "Проект провалится"
        val uri = backUri ?: "/viewProjects"

        val viewModel = ProjectDetailedVM(
            findProject,
            uri,
            investments.take(5),
            totalAmount,
            remaining,
            investments.count(),
            successForecast,
            amountPerDay,
            isClosed
        )
        Response(Status.OK).with(htmlView(request) of viewModel)
    }

fun listProjectsViewHandler(
    projectListOperation: ProjectListOperation,
    businessmanListOperation: UsersListOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val businessman = ProjectSearchFilter.businessmanField(request)
        val bNames = businessmanListOperation.list(TypesEnum.BUSINESSMAN)
        val businessmanId =
            if (businessman == "Select all") null else bNames.firstOrNull { it.name == businessman }

        var fundSizeStr = ProjectSearchFilter.fundSizeField(request)
        val fundSize = fundSizeStr.filter { it.isDigit() }.ifEmpty { "0" }.toInt()
        val fundSizeSign = fundSizeStr.filter { !it.isDigit() && (it == '<' || it == '>') }.ifEmpty { "=" }
        fundSizeStr = if (fundSizeSign + fundSize.toString() == "=0") "" else fundSizeSign + fundSize.toString()

        val startDateL = lensOrNull(ProjectSearchFilter.startDateLField, request)?.atStartOfDay()
        val startDateR = lensOrNull(ProjectSearchFilter.startDateRField, request)?.atStartOfDay()

        val endDateL = lensOrNull(ProjectSearchFilter.endDateLField, request)?.atStartOfDay()
        val endDateR = lensOrNull(ProjectSearchFilter.endDateRField, request)?.atStartOfDay()

        val projectIsClosed = ProjectSearchFilter.projectIsClosed(request)

        val filterExpr =
            makeProjectFilterExpr(businessmanId?.id, fundSize, fundSizeSign, startDateL, startDateR, endDateL, endDateR, projectIsClosed != null)

        val pageTotal = projectListOperation.getPagesCount { filterExpr }
        val index = Query.int().defaulted("page", 1)
        val page = index(request)

        val pagination = projectListOperation.listPaginationFiltered(page - 1) { filterExpr };

        val viewModel =
            ProjectListVM(
                pagination,
                page,
                pageTotal,
                ProjectFilterParams(
                    fundSizeStr,
                    startDateL?.toLocalDate(),
                    startDateR?.toLocalDate(),
                    endDateL?.toLocalDate(),
                    endDateR?.toLocalDate(),
                    bNames.map { it.name },
                    businessman,
                    projectIsClosed == null
                ),
                request.uri.query.replace(
                    "&page=\\d+".toRegex(),
                    ""
                ), //save search parameters and page when the page changes
                request.uri.toString().replace("?", "*").replace("&", "~") //encoding a uri for bakuri
            )

        Response(Status.OK).with(htmlView(request) of viewModel)
    }