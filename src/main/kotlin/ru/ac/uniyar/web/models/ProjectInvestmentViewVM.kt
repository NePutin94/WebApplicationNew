package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.ProjectInvestment

data class ProjectInvestmentViewVM(val investments: Iterable<ProjectInvestment>) : ViewModel
