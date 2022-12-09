package ru.ac.uniyar.web.handlers.authorization

import org.http4k.cloudnative.env.Environment
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.RequestContextLens
import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.authorization.JwtTools
import ru.ac.uniyar.domain.operations.commands.UserAddOperation
import ru.ac.uniyar.domain.operations.queries.UsersListOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.UsetState
import ru.ac.uniyar.web.filters.RegistrationFilter
import ru.ac.uniyar.web.filters.lensOrNull
import ru.ac.uniyar.web.models.authorization.RegistrationVM
import java.lang.Exception
import java.sql.Date

fun RegistrationHandler(
    contextLens: RequestContextLens<UsetState?>,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val tt = contextLens(request)
        val viewModel = RegistrationVM()
        Response(Status.OK).with(htmlView(request) of viewModel)
    }

fun RegistrationFormHandler(
    contextLens: RequestContextLens<UsetState?>,
    jwt: JwtTools,
    appEnv: Environment,
    addUser: UserAddOperation,
    userList: UsersListOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val validForm = RegistrationFilter.registrationLens(request)
        val showError = { form: WebForm ->
            val viewModel = RegistrationVM(form)
            Response(Status.OK).with(htmlView(request) of viewModel)
        }
        if (validForm.errors.isEmpty()) {
            val fname = RegistrationFilter.nameField(validForm).trim()
            val fpass = RegistrationFilter.passField(validForm).trim()
            val fpassCheck = RegistrationFilter.passCheckField(validForm).trim()
            if (fpass != fpassCheck)
                showError(validForm)
            else {
                val id = addUser.add(fname, fpass, 1, appEnv["auth.salt"].toString())
                try {
                    val token = jwt.create(id.toString())
                    Response(Status.FOUND).header("Location", "/registration")
                        .cookie(Cookie("auth_token", token!!))
                } catch (ex: Exception) {
                    throw Exception("ERROR")
                    //showError(validForm)
                }
            }
        } else {
            showError(validForm)
        }
    }