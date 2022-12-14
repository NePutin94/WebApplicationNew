package ru.ac.uniyar.web.handlers.project

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.UserTypesEnum
import ru.ac.uniyar.domain.operations.commands.ProjectAddOperation
import ru.ac.uniyar.domain.operations.commands.UserUpdateOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.ProjectFilters
import ru.ac.uniyar.web.models.project.ProjectAddVM
import java.time.LocalDateTime

fun addProjectViewHandler(
    contextLens: RequestContextLens<UsetState?>,
    htmlView: ContextAwareViewRender
): HttpHandler = { request ->
    val userC = contextLens(request)!!
    val viewModel = ProjectAddVM(userC.user.name)
    Response(Status.OK).with(htmlView(request) of viewModel)
}

fun addProjectFormHandler(
    contextLens: RequestContextLens<UsetState?>,
    userUpdate: UserUpdateOperation,
    projectAddOperation: ProjectAddOperation,
    htmlView: ContextAwareViewRender
): HttpHandler = { request ->
    val validForm = ProjectFilters.projectLens(request)
    val userC = contextLens(request)!!
    if (validForm.errors.isEmpty()) {
        val fname = ProjectFilters.nameField(validForm).trim()
        val fdesc = ProjectFilters.descField(validForm).trim()
        val ffundSize = ProjectFilters.fundSizeField(validForm)
        val fstartDate = ProjectFilters.startDateField(validForm)
        val fendDate = ProjectFilters.endDateField(validForm)
        val fcreationDate = LocalDateTime.now().withNano(0).withSecond(0)
        val id = projectAddOperation.add(Project {
            creationDate = fcreationDate
            name = fname
            user.id = userC.user.id
            description = fdesc
            fundSize = ffundSize
            startDate = fstartDate
            endDate = fendDate
        })
        if (userC.user.type.id == UserTypesEnum.AUTHORIZED.value) //type: auth -> businessman
            userUpdate.update(userC.user.id, UserTypesEnum.BUSINESSMAN.value)
        Response(Status.FOUND).header("Location", "/viewProjects/$id")
    } else {
        val viewModel = ProjectAddVM(
            "",
            validForm,
        )
        Response(Status.OK).with(htmlView(request) of viewModel)
    }
}