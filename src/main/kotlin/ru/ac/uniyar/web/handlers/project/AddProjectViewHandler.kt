package ru.ac.uniyar.web.handlers.project

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.template.ViewModel
import ru.ac.uniyar.Project
import ru.ac.uniyar.domain.operations.commands.ProjectAddOperation
import ru.ac.uniyar.domain.operations.queries.BusinessmanListOperation
import ru.ac.uniyar.web.filters.ProjectFilters
import ru.ac.uniyar.web.models.project.ProjectAddVM
import java.time.LocalDateTime

fun addProjectViewHandler(
    htmlView: BiDiBodyLens<ViewModel>,
    businessmanListOperation: BusinessmanListOperation
): HttpHandler = { request ->
    val businessmans = businessmanListOperation.list()
    val seletedB = Query.string().defaulted("businessman", "None")(request)
    val viewModel = ProjectAddVM(businessmans.map { it.name }, seletedB)
    Response(Status.OK).with(htmlView of viewModel)
}

fun addProjectFormHandler(
    projectAddOperation: ProjectAddOperation,
    businessmanListOperation: BusinessmanListOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler = { request ->
    val validForm = ProjectFilters.projectLens(request)
    val seletedB = Query.string().defaulted("businessman", "None")(request)
    val businessmans = businessmanListOperation.list()
    if (validForm.errors.isEmpty() && (seletedB != "None")) {
        val fname = ProjectFilters.nameField(validForm).trim()
        val fdesc = ProjectFilters.descField(validForm).trim()
        val ffundSize = ProjectFilters.fundSizeField(validForm)
        val fstartDate = ProjectFilters.startDateField(validForm)
        val fendDate = ProjectFilters.endDateField(validForm)
        val fcreationDate = LocalDateTime.now().withNano(0).withSecond(0)
        val find = businessmans.find { it.name == seletedB }!!.id;
        val id = projectAddOperation.add(Project {
            creationDate = fcreationDate
            name = fname
            businessman.id = businessmans.find { it.name == seletedB }!!.id
            description = fdesc
            fundSize = ffundSize
            startDate = fstartDate
            endDate = fendDate
        })
        Response(Status.FOUND).header("Location", "/viewProjects/$id")
    } else {
        val viewModel = ProjectAddVM(businessmans.map { it.name }, seletedB, validForm, if(seletedB == "None") "Выберите имя бизнесмена" else null)
        Response(Status.OK).with(htmlView of viewModel)
    }
}