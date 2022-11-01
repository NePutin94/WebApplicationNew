package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import ru.ac.uniyar.domain.operations.commands.BusinessmanAddOperation
import ru.ac.uniyar.domain.operations.commands.ProjectAddOperation
import ru.ac.uniyar.domain.operations.commands.InvestmentAddOperation
import ru.ac.uniyar.domain.operations.queries.*

class OperationHolder(database: Database) {
    //query
    val listProjectsOperation = ProjectListOperation(database, 6)
    val listInvestmentOperation = InvestmentListOperation(database, 10)
    val businessmanListOperation = BusinessmanListOperation(database)
    val fetchProjectOperation = FetchProjectOperation(database)
    val businessmanFetchOperation = BusinessmanFetchOperation(database)
    val fetchInvestments = InvestmentFetchOperation(database)
    //commands
    val addProjectOperation = ProjectAddOperation(database)
    val addBusinessman = BusinessmanAddOperation(database)
    val addProjectInvestOperation = InvestmentAddOperation(database)
}