package ru.ac.uniyar.web.handlers.businessman

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.routing.path
import ru.ac.uniyar.domain.entities.UserTypesEnum
import ru.ac.uniyar.domain.operations.queries.*
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.models.businessman.BusinessmanListVM
import ru.ac.uniyar.web.models.businessman.BusinessmenDetailedVM
import java.time.LocalDateTime

fun listBusinessmanViewHandler(
    businessmanListOperation: UsersListOperation,
    projectListOperation: ProjectListOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val businessmans = businessmanListOperation.list(UserTypesEnum.BUSINESSMAN)
        val projectsCount: MutableMap<String, Int> = mutableMapOf()
        for (p in businessmans) {
            val projects =
                projectListOperation.listByBusinessmanId(p.id)
            projectsCount[p.name] = projects.count()
        }
        val viewModel = BusinessmanListVM(businessmans, projectsCount)
        Response(Status.OK).with(htmlView(request) of viewModel)
    }

fun detailedBusinessmanHandler(
    invList: UserProjectInvestment,
    projectListOperation: ProjectListOperation,
    businessmanFetchOperation: UserFetchOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val businessmanId = request.path("id")
        if (businessmanId == null) {
            Response(Status.BAD_REQUEST)
        } else {
            val listOfProject = projectListOperation.listByBusinessmanId(businessmanId.toInt())
            val businessman = businessmanFetchOperation.fetch(businessmanId.toInt())!!
            val projectInvs = invList.list(businessmanId.toInt()).map { it.key.id to it.value }.toMap()

            val projectsSum: MutableMap<String, Int> = mutableMapOf()
            val successProjects: MutableMap<String, Boolean> = mutableMapOf()
            val projectClosed: MutableMap<String, Boolean> = mutableMapOf()

            for (p in listOfProject) {
                if (projectInvs.containsKey(p.id)) {
                    val investments = projectInvs[p.id]!!
                    projectsSum[p.name] = investments.sumOf { it.amount }
                    successProjects[p.name] = projectsSum[p.name]!! > p.fundSize
                    projectClosed[p.name] = p.endDate <= LocalDateTime.now()
                } else {
                    projectsSum[p.name] = 0
                    successProjects[p.name] = projectsSum[p.name]!! > p.fundSize
                    projectClosed[p.name] = p.endDate <= LocalDateTime.now()
                }
            }
            val viewModel = BusinessmenDetailedVM(
                businessman,
                listOfProject,
                projectsSum,
                successProjects,
                projectClosed
            )
            Response(Status.OK).with(htmlView(request) of viewModel)
        }
    }