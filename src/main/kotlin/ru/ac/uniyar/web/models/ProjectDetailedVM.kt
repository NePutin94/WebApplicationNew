package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.Project
import ru.ac.uniyar.ProjectInvestment
import java.util.StringJoiner

data class ProjectDetailedVM(
    val project: Project,
    val backUri: String,
    val investmentList: Iterable<ProjectInvestment>,
    val AmountCollected : Int,
    val RemainingTime : Long,
    val NumberInvestments : Int,
    val SuccessForecast : Boolean,
    val AmountPerDat : Long,
    val IsClosed : Boolean
) : ViewModel