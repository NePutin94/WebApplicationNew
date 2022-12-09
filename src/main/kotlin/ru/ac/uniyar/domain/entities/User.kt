package ru.ac.uniyar.domain.entities

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface User : Entity<User> {
    var creationDate: LocalDateTime
    var name: String
    var password: String
    val type: Int
    val id: Int

    companion object : Entity.Factory<User>()
}