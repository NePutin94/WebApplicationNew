package ru.ac.uniyar.web.handlers.uesr

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.ac.uniyar.domain.database.filters.projectInvestmentFilterExpr
import ru.ac.uniyar.domain.operations.queries.ProjectListOperation
import ru.ac.uniyar.domain.operations.queries.UserProjectInvestment
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.UserProjectFilter
import ru.ac.uniyar.web.filters.lensOrNull
import ru.ac.uniyar.web.models.user.UserViewVM


fun viewUserHandler(
    projectList: ProjectListOperation,
    invList: UserProjectInvestment,
    context: RequestContextLens<UsetState?>,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val userContext = context(request)
        if (userContext == null) {
            Response(Status.BAD_REQUEST)
        } else {
            val projectIsClosed = UserProjectFilter.projectIsClosed(request) != null
            val endDateL = lensOrNull(UserProjectFilter.endDateLField, request)?.atStartOfDay()
            val endDateR = lensOrNull(UserProjectFilter.endDateRField, request)?.atStartOfDay()

            val filterExpr = projectInvestmentFilterExpr(endDateL, endDateR)
            val invs = invList.list(userContext.id.toInt(), projectIsClosed) { filterExpr }

            val projectsInvestSum: MutableMap<Int, Int> = mutableMapOf()
            for (inv in invs)
                projectsInvestSum[inv.key.id] = inv.value.sumOf { it.amount }

            val projects = projectList.listByBusinessmanId(userContext.user.id)
            val viewModel = UserViewVM(
                userContext.user,
                invs,
                projectsInvestSum,
                projects,
                endDateL?.toLocalDate(),
                endDateR?.toLocalDate(),
                projectIsClosed
            )
            Response(Status.OK).with(htmlView(request) of viewModel)
        }
    }