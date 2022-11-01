package ru.ac.uniyar

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Project : Entity<Project> {
    var creationDate: LocalDateTime
    var name: String
    var businessman: Businessman
    var description: String
    var fundSize: Int
    var startDate: LocalDateTime
    var endDate: LocalDateTime
    val id: Int

    companion object : Entity.Factory<Project>()
}