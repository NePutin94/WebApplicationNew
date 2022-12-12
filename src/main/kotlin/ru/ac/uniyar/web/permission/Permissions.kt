package ru.ac.uniyar.web.permission

import ru.ac.uniyar.domain.entities.UserTypesEnum

data class Permissions(
    val canAddInvestment: Boolean = false,
    val canViewUserPage: Boolean = false,
    val canCreateProject: Boolean = false,
    val canViewUserProjects: Boolean = false,
    val canEditProject: Boolean = false,
    val canCloseProject: Boolean = false,
    val canDeleteProject: Boolean = false,
    val canViewInvestmentsOfProject: Boolean = false,
    val canViewBusinessmans : Boolean = true
)
val Guest = Permissions()
val AuthorizedUserPerm = Permissions(true, true, true, canViewInvestmentsOfProject = true, canViewBusinessmans = true)
val BusinessmanPerm = Permissions(
    true, true, true, true,
    true, true, true, true, true
)

fun setPerm(type: UserTypesEnum): Permissions {
    return when (type) {
        UserTypesEnum.AUTHORIZED -> AuthorizedUserPerm
        UserTypesEnum.BUSINESSMAN -> BusinessmanPerm
        else -> Guest
    }
}