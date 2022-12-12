package ru.ac.uniyar.web.models.user

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.entities.Project

data class UserViewProjectVM(
    val project: Project,
    val investmentList: Iterable<Investment>,
    val AmountCollected: Int,
    val RemainingTime: Long,
    val NumberInvestments: Int,
    val SuccessForecast: String,
    val AmountPerDat: Int,
    val IsClosed: Boolean,
    val form: WebForm = WebForm()
) : ViewModel