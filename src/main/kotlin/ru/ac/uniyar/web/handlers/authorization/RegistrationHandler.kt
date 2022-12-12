package ru.ac.uniyar.web.handlers.authorization

import org.http4k.cloudnative.env.Environment
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import org.http4k.lens.WebForm
import ru.ac.uniyar.domain.authorization.JwtTools
import ru.ac.uniyar.domain.entities.UserType
import ru.ac.uniyar.domain.operations.commands.UserAddOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.RegistrationFilter
import ru.ac.uniyar.web.models.authorization.RegistrationVM

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
    jwt: JwtTools,
    appEnv: Environment,
    addUser: UserAddOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val validForm = RegistrationFilter.registrationLens(request)
        val showError = { form: WebForm, error: String ->
            val viewModel = RegistrationVM(form, error)
            Response(Status.OK).with(htmlView(request) of viewModel)
        }
        if (validForm.errors.isEmpty()) {
            val fname = RegistrationFilter.nameField(validForm).trim()
            val fpass = RegistrationFilter.passField(validForm).trim()
            val ffeedback = RegistrationFilter.feedbackField(validForm).trim()
            val fpassCheck = RegistrationFilter.passCheckField(validForm).trim()
            if (fpass != fpassCheck)
                showError(validForm, "")
            else {
                try {
                    val id = addUser.add(ru.ac.uniyar.domain.entities.User {
                        name = fname
                        password = fpass
                        feedback = ffeedback
                        type = UserType { id = 1 }
                    }, appEnv["auth.salt"].toString())
                    val token = jwt.create(id.toString())
                    Response(Status.FOUND).header("Location", "/registration")
                        .cookie(Cookie("auth_token", token!!))
                } catch (ex: Exception) {
                    showError(validForm, ex.message ?: "")
                }
            }
        } else {
            showError(validForm, "")
        }
    }