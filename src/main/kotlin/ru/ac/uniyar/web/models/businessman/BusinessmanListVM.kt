package ru.ac.uniyar.web.models.businessman

import org.http4k.template.ViewModel
import ru.ac.uniyar.Businessman

data class BusinessmanListVM (val businessmans : Iterable<Businessman>, val projectsCount : Map<String, Int>) : ViewModel