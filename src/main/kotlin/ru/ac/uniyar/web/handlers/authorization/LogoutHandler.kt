package ru.ac.uniyar.web.handlers.authorization

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun LogoutHandler(): HttpHandler =
    {
        val date = LocalDateTime.of(1970, 1, 1, 0, 0)
        Response(Status.FOUND).header("Location", "/")
            .cookie(Cookie("auth_token", "", -1, date.atZone(ZoneId.systemDefault()).toInstant()))
        //remove cookie does not work
    }