package ru.ac.uniyar.domain.database

import org.http4k.cloudnative.env.Environment
import ru.ac.uniyar.domain.entities.*
import ru.ac.uniyar.domain.operations.OperationHolder
import java.time.LocalDateTime

fun fill(opHolder: OperationHolder, appEnv : Environment) {
    val addProject = opHolder.addProjectOperation
    val invest = opHolder.addProjectInvestOperation
    val findP = opHolder.fetchProjectOperation
    val addUser = opHolder.addUser
    val getUser = opHolder.userFetch
    val userTFetch = opHolder.userTypeFetch

    val Ivan = addUser.add(User {
        name = "Ivan"
        password = "1234"
        feedback = "test@mail.ru"
        type = userTFetch.fetch(TypesEnum.BUSINESSMAN)!!
    }, appEnv["auth_salt"].toString())

    val Sergey = addUser.add(User {
        name = "Sergey"
        password = "4321"
        feedback = "sadas@mail.ru"
        type = userTFetch.fetch(TypesEnum.BUSINESSMAN)!!
    }, appEnv["auth_salt"].toString())

    val CarCompany = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Car company"
        user = getUser.fetch(Ivan as Int)!!
        //businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2018, 5, 31, 12, 14)
        fundSize = 19000
        endDate = LocalDateTime.of(2023, 2, 20, 8, 0)
        description = "Car sales Car sales Car sales Car sales Car sales Car sales"
    })

    val Medicines = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Medicines"
        user = getUser.fetch(Ivan as Int)!!
        //businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2019, 5, 31, 12, 14)
        fundSize = 23000
        endDate = LocalDateTime.of(2024, 2, 20, 8, 0)
        description = "Medicines Medicines Medicines"
    })

    val HelpingPeople = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Helping people"
        user = getUser.fetch(Ivan as Int)!!
        startDate = LocalDateTime.of(2021, 5, 31, 12, 14)
        fundSize = 20000
        endDate = LocalDateTime.of(2028, 1, 20, 8, 0)
        description = "Helping people Helping people Helping people Helping people "
    })

    val Ecology = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Ecology"
        user = getUser.fetch(Ivan as Int)!!
        startDate = LocalDateTime.of(2021, 5, 31, 12, 14)
        fundSize = 20000
        endDate = LocalDateTime.of(2028, 1, 20, 8, 0)
        description = "Ecology Ecology Ecology Ecology Ecology Ecology Ecology Ecology Ecology Ecology Ecology"
    })

    val Technologies = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Technologies"
        user = getUser.fetch(Ivan as Int)!!
        startDate = LocalDateTime.of(2018, 5, 31, 12, 14)
        fundSize = 20000
        endDate = LocalDateTime.of(2021, 1, 20, 8, 0)
        description = "Technologies Technologies Technologies Technologies Technologies Technologies Technologies Technologies"
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 1, 31, 12, 14)
        project = findP.fetch(Technologies as Int)!!
        invName = "Ivan"
        feedback = "asd@mail.ru"
        amount = 1000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 31, 12, 14)
        project = findP.fetch(Technologies as Int)!!
        invName = "Ivan"
        amount = 900
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 2, 12, 14)
        project = findP.fetch(Technologies as Int)!!
        invName = "Ivan"
        amount = 8000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 10, 20, 12, 14)
        project = findP.fetch(CarCompany as Int)!!
        amount = 10000
        invName = "Ivan"
        feedback = "test@mail.ru"
    })

}