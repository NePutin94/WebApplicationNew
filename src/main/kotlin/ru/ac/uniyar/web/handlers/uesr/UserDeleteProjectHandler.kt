package ru.ac.uniyar.web.handlers.uesr

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.lens.RequestContextLens
import org.http4k.routing.path
import ru.ac.uniyar.domain.operations.commands.ProjectDeleteOperation
import ru.ac.uniyar.domain.operations.queries.FetchProjectOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState

fun UserDeleteProjectHandler(
    fetchProjectOp: FetchProjectOperation,
    projectDeleteOp: ProjectDeleteOperation,
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
            projectDeleteOp.delete(projectId)
            Response(Status.FOUND).header("Location", "/viewUser/")
        } catch (ex: Exception) {
            Response(Status.BAD_REQUEST)
        }
    }