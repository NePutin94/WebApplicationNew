package ru.ac.uniyar.web.models.project

import org.http4k.template.ViewModel
import ru.ac.uniyar.Project
import ru.ac.uniyar.Investment

data class ProjectDetailedVM(
    val project: Project,
    val backUri: String,
    val investmentList: Iterable<Investment>,
    val AmountCollected : Int,
    val RemainingTime : Long,
    val NumberInvestments : Int,
    val SuccessForecast : Boolean,
    val AmountPerDat : Int,
    val IsClosed : Boolean
) : ViewModel