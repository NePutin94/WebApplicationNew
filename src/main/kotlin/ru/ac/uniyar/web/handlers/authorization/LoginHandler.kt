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
import ru.ac.uniyar.domain.operations.queries.UserFetchOperation
import ru.ac.uniyar.util.ContextAwareViewRender
import ru.ac.uniyar.web.context.UsetState
import ru.ac.uniyar.web.filters.LoginFilter
import ru.ac.uniyar.web.filters.RegistrationFilter
import ru.ac.uniyar.web.models.authorization.LoginVM


fun LoginHandler(
    contextLens: RequestContextLens<UsetState?>,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val tt = contextLens(request)
        val viewModel = LoginVM()
        Response(Status.OK).with(htmlView(request) of viewModel)
    }

fun LoginFormHandler(
    jwt: JwtTools,
    appEnv: Environment,
    userList: UserFetchOperation,
    htmlView: ContextAwareViewRender
): HttpHandler =
    { request ->
        val validForm = LoginFilter.loginLens(request)
        val showError = { form: WebForm, userFound: Boolean ->
            val viewModel = LoginVM(form, userFound)
            Response(Status.OK).with(htmlView(request) of viewModel)
        }
        if (validForm.errors.isEmpty()) {
            val fname = RegistrationFilter.nameField(validForm).trim()
            val fpass = RegistrationFilter.passField(validForm).trim()
            val id = userList.fetch(fname, fpass, appEnv["auth.salt"].toString());
            if (id == null) {
                showError(validForm, false)
            } else {
                val token = jwt.create(id.toString())
                Response(Status.FOUND).header("Location", "/").cookie(Cookie("auth_token", token!!))
            }
        } else {
            showError(validForm, true)
        }
    }