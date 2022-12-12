package ru.ac.uniyar.domain.entities

import org.ktorm.entity.Entity

interface UserType : Entity<UserType> {
    var name: String
    var id: Int

    companion object : Entity.Factory<UserType>()
}

enum class UserTypesEnum(val value: Int) {
    AUTHORIZED(1),
    BUSINESSMAN(2);
    companion object {
        fun fromInt(value: Int) = UserTypesEnum.values().first { it.value == value }
    }
}