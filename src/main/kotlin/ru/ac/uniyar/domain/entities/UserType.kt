package ru.ac.uniyar.domain.entities

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface UserType : Entity<UserType> {
    var name: String
    var id: Int

    companion object : Entity.Factory<UserType>()
}

enum class TypesEnum(val value: Int) {
    AUTHORIZED(1),
    BUSINESSMAN(2);
    companion object {
        fun fromInt(value: Int) = TypesEnum.values().first { it.value == value }
    }
}