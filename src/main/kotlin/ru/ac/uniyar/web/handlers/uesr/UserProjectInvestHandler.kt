package ru.ac.uniyar.web.handlers.uesr

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import org.http4k.routing.path
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.domain.operations.queries.InvestmentListOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.models.user.UserViewProjInvestVM


fun UserProjectInvestHandler(
    fetchProjectOp: FetchProjectOperation,
    invListOp: InvestmentListOperation,
    contextLens: RequestContextLens<UsetState?>,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val projectId: Int
        try {
            projectId = request.path("id")!!.toInt() //catch
            val project = fetchProjectOp.fetch(projectId) ?: throw Exception("unable to find the project")
            val userContext = contextLens(request)!!
            if (project.user.id != userContext.user.id)
                throw Exception("another user's project")
            val invList = invListOp.listByUserProject(userContext.user.name, projectId)
            val viewModel = UserViewProjInvestVM(userContext.user, project, invList)
            Response(Status.OK).with(htmlView(request) of viewModel)
        } catch (ex: Exception) {
            Response(Status.BAD_REQUEST)
        }
    }