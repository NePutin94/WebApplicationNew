package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.database.InvestmentTable
import ru.ac.uniyar.domain.database.ProjectTable
import ru.ac.uniyar.domain.database.UsersTable
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.User
import java.lang.Exception
import java.time.LocalDateTime

class UserProjectInvestment(
    private val database: Database
) {
    fun list(userId: Int): Map<Project, List<Investment>> {
        val user = database.from(UsersTable).select().where { UsersTable.id eq userId }
            .mapNotNull { row -> UsersTable.createEntity(row) }.firstOrNull() ?: throw Exception("User not found")
        val invs = database
            .from(InvestmentTable)
            .leftJoin(ProjectTable, on = ProjectTable.id eq InvestmentTable.project)
            .select()
            .where { InvestmentTable.invName eq user.name }
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

        return invs.groupBy { it.project }
    }

    fun listTest(userId: Int): Map<Project, List<Investment>> {
        val user = database.from(UsersTable).select().where { UsersTable.id eq userId }
            .mapNotNull { row -> UsersTable.createEntity(row) }.firstOrNull() ?: throw Exception("User not found")

        val invs = database
            .from(ProjectTable)
            .leftJoin(InvestmentTable, on = ProjectTable.id eq InvestmentTable.project)
            .select()
            .where { InvestmentTable.invName eq user.name }
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

        return invs.groupBy { it.project }
    }


    fun list(userId: Int, filter: () -> ColumnDeclaring<Boolean>): Map<Project, List<Investment>> {
        val user = database.from(UsersTable).select().where { UsersTable.id eq userId }
            .mapNotNull { row -> UsersTable.createEntity(row) }.firstOrNull() ?: throw Exception("User not found")
        val date = LocalDateTime.now()
        val invs = database
            .from(InvestmentTable)
            .joinReferencesAndSelect()
            .where { InvestmentTable.invName eq user.name }
            .where(filter)
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

        return invs.groupBy { it.project }
    }

    fun list(
        userId: Int,
        projectCloseDesc: Boolean,
        filter: () -> ColumnDeclaring<Boolean>
    ): Map<Project, List<Investment>> {
        val user = database.from(UsersTable).select().where { UsersTable.id eq userId }
            .mapNotNull { row -> UsersTable.createEntity(row) }.firstOrNull() ?: throw Exception("User not found")
        val date = LocalDateTime.now()
        if (projectCloseDesc) {
            val invs = database
                .from(InvestmentTable)
                .leftJoin(ProjectTable, on = ProjectTable.id eq InvestmentTable.project)
                .select()
                .where { InvestmentTable.invName eq user.name }
                .where(filter)
                .orderBy((ProjectTable.enddate lessEq date).desc())
                .mapNotNull { row -> InvestmentTable.createEntity(row) }

            return invs.groupBy { it.project }
        }

        val invs = database
            .from(InvestmentTable)
            .leftJoin(ProjectTable, on = ProjectTable.id eq InvestmentTable.project)
            .select()
            .where { InvestmentTable.invName eq user.name }
            .where(filter)
            .orderBy((ProjectTable.enddate lessEq date).asc())
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

        return invs.groupBy { it.project }
    }
}