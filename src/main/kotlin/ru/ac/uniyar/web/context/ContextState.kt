package ru.ac.uniyar.web.context

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.entities.User


data class UsetState(val id: String, val user: User) : ViewModel
