package ru.ac.uniyar.domain.entities

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Investment : Entity<Investment> {
    val id: Int
    var creationDate: LocalDateTime
    var project: Project
   // var user: User?
    var invName: String?
    var feedback: String?
    var amount: Int

    companion object : Entity.Factory<Investment>()
}