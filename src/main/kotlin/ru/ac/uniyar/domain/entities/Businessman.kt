package ru.ac.uniyar.domain.entities

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Businessman : Entity<Businessman> {
    var creationDate: LocalDateTime
    var name: String
    var id: Int

    companion object : Entity.Factory<Businessman>()
}