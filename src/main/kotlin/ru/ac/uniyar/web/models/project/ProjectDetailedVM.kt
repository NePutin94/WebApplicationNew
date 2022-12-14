package ru.ac.uniyar.web.models.project

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.Investment

data class ProjectDetailedVM(
    val project: Project,
    val investmentList: Iterable<Investment>,
    val AmountCollected : Int,
    val RemainingTime : Long,
    val NumberInvestments : Int,
    val SuccessForecast : String,
    val AmountPerDat : Int,
    val IsClosed : Boolean
) : ViewModel