package ru.ac.uniyar.web.handlers.uesr

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import org.http4k.routing.path
import ru.ac.uniyar.domain.operations.commands.ProjectUpdateOperation
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.domain.operations.queries.InvestmentListOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.ProjectEditFilter
import ru.ac.uniyar.web.models.user.UserViewProjectVM
import java.time.Duration
import java.time.LocalDateTime

fun UserViewProjectHandler(
    projectFetchOp: FetchProjectOperation,
    listInvestmentOp: InvestmentListOperation,
    contextLens: RequestContextLens<UsetState?>,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val projectId: Int
        try {
            projectId = request.path("id")!!.toInt() //catch
            val project = projectFetchOp.fetch(projectId) ?: throw Exception("unable to find the project")
            val userContext = contextLens(request)!!
            if (project.user.id != userContext.user.id)
                throw Exception("another user's project")
            val investments = listInvestmentOp.listByProjectId(projectId.toInt())
            val totalAmount = investments.sumOf { it.amount }

            val isClosed = project.endDate <= LocalDateTime.now()
            val remaining = Duration.between(LocalDateTime.now(), project.endDate).toDays()

            val deltaDate = Duration.between(project.startDate, project.endDate).toDays()
            val amountPerDay = (totalAmount / deltaDate).toInt()

            val successForecast =
                if ((project.fundSize - totalAmount) - remaining * amountPerDay <= 0) "Проект будет успешен" else "Проект провалится"

            val viewModel = UserViewProjectVM(
                project,
                investments.take(5),
                totalAmount,
                remaining,
                investments.count(),
                successForecast,
                amountPerDay,
                isClosed
            )
            Response(Status.OK).with(htmlView(request) of viewModel)
        } catch (ex: Exception) {
            Response(Status.BAD_REQUEST)
        }
    }

fun UserViewProjectFormHandler(
    projectFetchOp: FetchProjectOperation,
    projectUpdateOp: ProjectUpdateOperation
): HttpHandler =
    { request ->
        val projectId: Int
        try {
            projectId = request.path("id")!!.toInt() //catch
            val project = projectFetchOp.fetch(projectId) ?: throw Exception("unable to find the project")
            val validForm = ProjectEditFilter.projectEditLens(request)
            if (validForm.errors.isEmpty()) {
                var fname = ProjectEditFilter.nameField(validForm)?.trim()
                var fdesc = ProjectEditFilter.descField(validForm)?.trim()
                var ffundSize = ProjectEditFilter.fundSizeField(validForm)
                var fstartDate = ProjectEditFilter.startDateField(validForm)
                var fendDate = ProjectEditFilter.endDateField(validForm)

                if (fname == null) {
                    fname = project.name
                }
                if (fdesc == null) {
                    fdesc = project.description
                }
                if (ffundSize == null) {
                    ffundSize = project.fundSize
                }
                if (fstartDate == null) {
                    fstartDate = project.startDate
                }
                if (fendDate == null) {
                    fendDate = project.endDate
                }

                projectUpdateOp.update(projectId, fname, fdesc, ffundSize, fstartDate, fendDate);

                Response(Status.FOUND).header("Location", "/userViewProject/${projectId}")
            } else {
                Response(Status.FOUND).header("Location", "/userViewProject/${projectId}")
            }
        } catch (ex: Exception) {
            Response(Status.BAD_REQUEST)
        }
    }