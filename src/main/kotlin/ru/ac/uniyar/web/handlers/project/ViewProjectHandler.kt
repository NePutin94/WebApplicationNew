package ru.ac.uniyar.web.handlers.project

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.path
import ru.ac.uniyar.domain.database.filters.makeProjectFilterExpr
import ru.ac.uniyar.domain.entities.UserTypesEnum
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.domain.operations.queries.InvestmentListOperation
import ru.ac.uniyar.domain.operations.queries.ProjectListOperation
import ru.ac.uniyar.domain.operations.queries.UsersListOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.filters.ProjectFilterParams
import ru.ac.uniyar.web.filters.ProjectSearchFilter
import ru.ac.uniyar.web.filters.lensOrNull
import ru.ac.uniyar.web.models.project.ProjectDetailedVM
import ru.ac.uniyar.web.models.project.ProjectListVM
import java.time.Duration
import java.time.LocalDateTime

fun detailedProjectViewHandler(
    projectFetchOperation: FetchProjectOperation,
    listInvestmentOperation: InvestmentListOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
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

        val viewModel = ProjectDetailedVM(
            findProject,
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
        val bNames = businessmanListOperation.list(UserTypesEnum.BUSINESSMAN)
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

        val pagination = projectListOperation.listPaginationFiltered(page - 1) { filterExpr }

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
                )
            )

        Response(Status.OK).with(htmlView(request) of viewModel)
    }