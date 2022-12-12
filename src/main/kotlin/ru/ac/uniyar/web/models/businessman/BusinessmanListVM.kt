package ru.ac.uniyar.web.models.businessman

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.entities.User

data class BusinessmanListVM (val businessmans : Iterable<User>, val projectsCount : Map<String, Int>) : ViewModel