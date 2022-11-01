package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.path
import org.http4k.template.ViewModel
import org.ktorm.dsl.eq
import ru.ac.uniyar.domain.database.InvestmentTable
import ru.ac.uniyar.domain.operations.queries.BusinessmanListOperation
import ru.ac.uniyar.domain.operations.queries.BusinessmanFetchOperation
import ru.ac.uniyar.domain.operations.queries.InvestmentFetchOperation
import ru.ac.uniyar.domain.operations.queries.ProjectListOperation
import ru.ac.uniyar.web.models.businessman.BusinessmanListVM
import ru.ac.uniyar.web.models.businessman.BusinessmenDetailedVM
import java.time.LocalDateTime

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

fun detailedBusinessmanHandler(
    projectListOperation: ProjectListOperation,
    businessmanFetchOperation: BusinessmanFetchOperation,
    investmentFetchOperation: InvestmentFetchOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->
        val backUriField = Query.string().optional("backUri")
        val backUri = backUriField(request)?.let { it.replace("*", "?").replace("~", "&") }
        val businessmanId = request.path("id")!!
        val listOfProject = projectListOperation.listByBusinessmanId(businessmanId.toInt())
        val businessman = businessmanFetchOperation.fetch(businessmanId.toInt())!!

        val projectsSum: MutableMap<String, Int> = mutableMapOf()
        val successProjects: MutableMap<String, Boolean> = mutableMapOf()
        val projectClosed: MutableMap<String, Boolean> = mutableMapOf()
        for (p in listOfProject) {
            val investments =
                investmentFetchOperation.fetchFiltered { InvestmentTable.project eq p.id }
            projectsSum[p.name] = investments.sumOf { it.amount }
            successProjects[p.name] = projectsSum[p.name]!! > p.fundSize
            projectClosed[p.name] = p.endDate <= LocalDateTime.now()
        }
        val viewModel = BusinessmenDetailedVM(businessman, backUri ?: "/viewBusinessman", listOfProject, projectsSum, successProjects, projectClosed)
        Response(Status.OK).with(htmlView of viewModel)
    }