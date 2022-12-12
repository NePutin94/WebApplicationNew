package ru.ac.uniyar.web.permission

import ru.ac.uniyar.domain.entities.TypesEnum

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
) {
    companion object {
        fun canAddInvestment(perm: Permissions): Boolean {
            return perm.canAddInvestment
        }
    }
}

val Guest = Permissions()
val AuthorizedUserPerm = Permissions(true, true, true, canViewInvestmentsOfProject = true)
val BusinessmanPerm = Permissions(
    true, true, true, true,
    true, true, true, true
)

fun setPerm(type: TypesEnum): Permissions {
    return when (type) {
        TypesEnum.AUTHORIZED -> AuthorizedUserPerm
        TypesEnum.BUSINESSMAN -> BusinessmanPerm
        else -> Guest
    }
}