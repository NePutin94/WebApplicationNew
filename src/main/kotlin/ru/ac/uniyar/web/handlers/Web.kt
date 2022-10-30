package ru.ac.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.lens.string
import org.http4k.routing.*
import org.http4k.template.ViewModel
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.database.ProjectInvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.domain.operations.queries.*
import ru.ac.uniyar.web.filters.ProjectFilterParams
import ru.ac.uniyar.web.filters.SearchFilter
import ru.ac.uniyar.web.models.*
import java.time.Duration
import java.time.LocalDateTime

fun mainViewHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = MainVM("Совместное финансирование")
    Response(Status.OK).with(htmlView of viewModel)
}

fun listProjectsViewHandler(
    projectListOperation: ProjectListOperation,
    fetchBusinessmanOperation: FetchBusinessmanOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->

        val name = SearchFilter.nameField(request)
        val businessman = SearchFilter.businessmanField(request)
        val fundSize = SearchFilter.fundSizeField(request)
        val sign = SearchFilter.sign(request)
        val startDateL = SearchFilter.startDateLField(request)?.atStartOfDay()
        val startDateR = SearchFilter.startDateRField(request)?.atStartOfDay()
        var filterExpr: ColumnDeclaring<Boolean> = ProjectTable.id eq 0 or true //always true

        if (!name.isNullOrEmpty())
            filterExpr = filterExpr.and(ProjectTable.name eq name)
        if (!businessman.isNullOrEmpty()) {
            val businessmanValue = fetchBusinessmanOperation.fetchByName(businessman)
            if (businessmanValue != null)
                filterExpr = filterExpr.and(ProjectTable.businessman eq businessmanValue.id)
        }
        if (fundSize != 0) {
            filterExpr = when (sign) {
                "<" -> {
                    filterExpr.and(ProjectTable.fundsize less fundSize)
                }

                ">" -> {
                    filterExpr.and(ProjectTable.fundsize greater fundSize)
                }

                else -> {
                    filterExpr.and(ProjectTable.fundsize eq fundSize)
                }
            }
        }
        if (startDateL != null && startDateR != null)
            filterExpr =
                filterExpr.and(ProjectTable.startdate between startDateL..startDateR)

        val pageTotal = projectListOperation.getPagesCount { filterExpr }
        val index = Query.int().defaulted("page", 1)
        var page = index(request)

        val viewModel =
            ProjectListVM(
                projectListOperation.listPaginationFiltered(page - 1) { filterExpr },
                page,
                pageTotal,
                ProjectFilterParams(
                    name,
                    businessman,
                    fundSize,
                    sign,
                    startDateL?.toLocalDate(),
                    startDateR?.toLocalDate(),
                    request.uri.toString().replace("?", "*").replace("&", "~")
                )
            )

        Response(Status.OK).with(htmlView of viewModel)
    }

fun listInvestmentViewHandler(
    investmentListOperation: InvestmentListOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->
        val pageTotal = investmentListOperation.list()
        val viewModel =
            ProjectInvestmentViewVM(pageTotal)
        Response(Status.OK).with(htmlView of viewModel)
    }

fun detailedInvestmentViewHandler(
    fetchInvestmentOperation: FetchInvestmentOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->
        val investId = request.path("id")!!.toInt()
        val investment = fetchInvestmentOperation.fetch(investId)!!
        val viewModel = InvestmentDetailedVM(investment)
        Response(Status.OK).with(htmlView of viewModel)
    }

fun addProjectViewHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = ProjectAddVM()
    Response(Status.OK).with(htmlView of viewModel)
}

fun addInvestmentHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = ProjectAddInvestmentVM()
    Response(Status.OK).with(htmlView of viewModel)
}

fun descViewHandler(htmlView: BiDiBodyLens<ViewModel>): HttpHandler = {
    val viewModel = ProjectDescVM("")
    Response(Status.OK).with(htmlView of viewModel)
}

fun detailedProjectViewHandler(
    projectFetchOperation: FetchProjectOperation,
    listInvestmentOperation: InvestmentListOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->
        val backUriField = Query.string().optional("backUri")
        val backUri = backUriField(request)?.let { it.replace("*", "?").replace("~", "&") }
        val projectId = request.path("id")!!
        val findProject = projectFetchOperation.fetch(projectId.toInt())!!
        val investments = listInvestmentOperation.listByProjectId(projectId.toInt())
        val totalAmount = investments.sumOf { it.amount }

        val isClosed = findProject.endDate < LocalDateTime.now()
        val remaining = Duration.between(findProject.startDate, findProject.startDate).toDays()

        val dates = investments.map { it.creationDate }
        val deltaDate = Duration.between(dates.min(), dates.max()).toDays()
        val amountPerDay = totalAmount / deltaDate
        val successForecast = findProject.fundSize - remaining * amountPerDay <= 0
        val uri = backUri ?: "/viewProjects"

        val viewModel = ProjectDetailedVM(
            findProject,
            uri,
            investments,
            totalAmount,
            remaining,
            investments.count(),
            successForecast,
            amountPerDay,
            isClosed
        )
        Response(Status.OK).with(htmlView of viewModel)
    }

fun detailedBusinessmanHandler(
    projectListOperation: ProjectListOperation,
    fetchBusinessmanOperation: FetchBusinessmanOperation,
    fetchInvestmentOperation: FetchInvestmentOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->
        val businessmanId = request.path("id")!!
        val listOfProject = projectListOperation.listByBusinessmanId(businessmanId.toInt())
        val businessman = fetchBusinessmanOperation.fetch(businessmanId.toInt())!!

        val projectsSum: MutableMap<String, Int> = mutableMapOf()
        val successProjects: MutableMap<String, Boolean> = mutableMapOf()
        for (p in listOfProject) {
            val investments =
                fetchInvestmentOperation.fetchFiltered { ProjectInvestmentTable.project eq p.id }
            projectsSum[p.name] = investments.sumOf { it.amount }
            successProjects[p.name] = projectsSum[p.name]!! > p.fundSize
        }

        val viewModel = BusinessmenDetailedVM(businessman, listOfProject, projectsSum, successProjects)
        Response(Status.OK).with(htmlView of viewModel)
    }

fun listBusinessmanViewHandler(
    businessmanListOperation: BusinessmanListOperation,
    projectListOperation: ProjectListOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->

        val businessmans = businessmanListOperation.list()
        val projectsCount: MutableMap<String, Int> = mutableMapOf()
        for (p in businessmans) {
            val projects =
                projectListOperation.listByBusinessmanId(p.id)
            projectsCount[p.name] = projects.count()
        }
        val viewModel = BusinessmanListVM(businessmans, projectsCount)
        Response(Status.OK).with(htmlView of viewModel)
    }

val Web: (operationHolder: OperationHolder, BiDiBodyLens<ViewModel>) -> RoutingHttpHandler =
    { operationHolder, htmlView ->
        routes(
            "/" bind Method.GET to mainViewHandler(htmlView),
            "/viewProjects" bind Method.GET to listProjectsViewHandler(
                operationHolder.listProjectsOperation,
                operationHolder.fetchBusinessmanOperation,
                htmlView
            ),
            "/help" bind Method.GET to descViewHandler(htmlView),
            "/viewBusinessman" bind Method.GET to listBusinessmanViewHandler(
                operationHolder.businessmanListOperation,
                operationHolder.listProjectsOperation,
                htmlView
            ),
            "/viewBusinessman/{id}" bind Method.GET to detailedBusinessmanHandler(
                operationHolder.listProjectsOperation,
                operationHolder.fetchBusinessmanOperation,
                operationHolder.fetchInvestments,
                htmlView
            ),
            "/makeInvestment" bind Method.GET to addInvestmentHandler(htmlView),
            "/viewInvestment" bind Method.GET to listInvestmentViewHandler(
                operationHolder.listInvestmentOperation,
                htmlView
            ),
            "/viewInvestment/{id}" bind Method.GET to detailedInvestmentViewHandler(
                operationHolder.fetchInvestments,
                htmlView
            ),
            "/addProject" bind Method.GET to addProjectViewHandler(htmlView),
            "/viewProjects/{id}" bind Method.GET to detailedProjectViewHandler(
                operationHolder.fetchProjectOperation,
                operationHolder.listInvestmentOperation,
                htmlView
            ),
        )
    }