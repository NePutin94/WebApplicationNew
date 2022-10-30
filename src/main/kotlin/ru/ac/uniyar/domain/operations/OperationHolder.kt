package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import ru.ac.uniyar.domain.operations.commands.BusinessmanAddOperation
import ru.ac.uniyar.domain.operations.commands.ProjectAddOperation
import ru.ac.uniyar.domain.operations.commands.ProjectInvestmentAddOperation
import ru.ac.uniyar.domain.operations.queries.*

class OperationHolder(database: Database) {
    //query
    val listProjectsOperation = ProjectListOperation(database, 3)
    val listProjectsFOperation = ProjectListFilterOperation(database)
    val listInvestmentOperation = InvestmentListOperation(database)
    val businessmanListOperation = BusinessmanListOperation(database)
    val fetchProjectOperation = FetchProjectOperation(database)
    val fetchBusinessmanOperation = FetchBusinessmanOperation(database)
    val fetchInvestments = FetchInvestmentOperation(database)
    //commands
    val addProjectOperation = ProjectAddOperation(database)
    val addBusinessman = BusinessmanAddOperation(database)
    val addProjectInvestOperation = ProjectInvestmentAddOperation(database)
}