package ru.ac.uniyar.domain.operations.queries

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.ColumnDeclaring
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.database.InvestmentTable
import ru.ac.uniyar.domain.database.UsersTable
import java.lang.Exception
import java.math.RoundingMode

class InvestmentListOperation(
    private val database: Database,
    val pageSize: Int
) {
    fun listFiltered(filter: () -> ColumnDeclaring<Boolean>): List<Investment> =
        database
            .from(InvestmentTable)
            .joinReferencesAndSelect()
            .where(filter)
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

    fun listPaginationFiltered(pageIndex: Int, filter: () -> ColumnDeclaring<Boolean>): List<Investment> =
        database
            .from(InvestmentTable)
            .joinReferencesAndSelect()
            .orderBy(InvestmentTable.creationdate.desc())
            .where(filter)
            .limit(pageIndex * pageSize, pageSize)
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

    fun list(): List<Investment> =
        database
            .from(InvestmentTable)
            .joinReferencesAndSelect()
            .orderBy(InvestmentTable.creationdate.desc())
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

    fun listByProjectId(projectId: Int): List<Investment> =
        database.from(InvestmentTable).joinReferencesAndSelect()
            .where { InvestmentTable.project eq projectId }
            .orderBy(InvestmentTable.creationdate.desc())
            .mapNotNull { row -> InvestmentTable.createEntity(row) }

    fun listByUserProject(userName: String, projectId: Int): List<Investment> {
        return database.from(InvestmentTable).joinReferencesAndSelect()
            .where { InvestmentTable.invName eq userName and (InvestmentTable.project eq projectId) }
            .orderBy(InvestmentTable.creationdate.desc())
            .mapNotNull { row -> InvestmentTable.createEntity(row) }
    }

    fun getTotalCount(filter: () -> ColumnDeclaring<Boolean>) = database
        .from(InvestmentTable)
        .joinReferencesAndSelect()
        .where(filter)
        .totalRecords

    fun getPagesCount(filter: () -> ColumnDeclaring<Boolean>): Int {
        val res = getTotalCount(filter).toDouble() / pageSize.toDouble()
        return res.toBigDecimal().setScale(0, RoundingMode.CEILING).toInt()
    }
}