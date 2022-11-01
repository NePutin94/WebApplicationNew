package ru.ac.uniyar

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Investment : Entity<Investment> {
    val id: Int
    var creationDate: LocalDateTime
    var project: Project
    var invName: String?
    var feedback: String?
    var amount: Int

    companion object : Entity.Factory<Investment>()
}