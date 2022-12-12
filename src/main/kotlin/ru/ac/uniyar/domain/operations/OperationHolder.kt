package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import ru.ac.uniyar.domain.operations.commands.*
import ru.ac.uniyar.domain.operations.queries.*

class OperationHolder(database: Database) {
    //query
    val listProjectsOperation = ProjectListOperation(database, 6)
    val listInvestmentOperation = InvestmentListOperation(database, 10)
    //val businessmanListOperation = BusinessmanListOperation(database)
    val fetchProjectOperation = FetchProjectOperation(database)
    //val businessmanFetchOperation = BusinessmanFetchOperation(database)
    val fetchInvestments = InvestmentFetchOperation(database)
    val usersList = UsersListOperation(database)
    val userFetch = UserFetchOperation(database)
    val userTypeFetch = UserTypeFetch(database)
    val userUpdate = UserUpdateOperation(database)
    val userInvs = UserProjectInvestment(database)
    //commands
    val addProjectOperation = ProjectAddOperation(database)
    //val addBusinessman = BusinessmanAddOperation(database)
    val addProjectInvestOperation = InvestmentAddOperation(database)
    val addUser = UserAddOperation(database)
    val projectUpdate = ProjectUpdateOperation(database)
    val projectDelete = ProjectDeleteOperation(database)
}