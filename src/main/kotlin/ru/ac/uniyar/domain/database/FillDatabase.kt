package ru.ac.uniyar.domain.database

import ru.ac.uniyar.Businessman
import ru.ac.uniyar.Project
import ru.ac.uniyar.Investment
import ru.ac.uniyar.domain.operations.OperationHolder
import java.time.LocalDateTime

fun fill(opHolder: OperationHolder) {
    val addOper = opHolder.addProjectOperation
    val addOper2 = opHolder.addBusinessman
    val findB = opHolder.businessmanFetchOperation
    val invest = opHolder.addProjectInvestOperation
    val findP = opHolder.fetchProjectOperation

    val Ivan = addOper2.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Ivan"
    })
    val Sergey = addOper2.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Sergey"
    })
    val Andey = addOper2.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Andey"
    })
    val Anton = addOper2.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Anton"
    })
    val Nastya = addOper2.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Nastya"
    })
    val Sonya = addOper2.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Sonya"
    })

    val CarCompany = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Car company"
        businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2018, 12, 31, 12, 14)
        fundSize = 19000
        endDate = LocalDateTime.of(2020, 5, 20, 8, 0)
        description = "Car sales Car sales Car sales Car sales Car sales Car sales"
    })
    val FlowerShop = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Flower Shop"
        businessman = findB.fetch(Sergey)!!
        startDate = LocalDateTime.of(2019, 1, 31, 12, 14)
        fundSize = 5000
        endDate = LocalDateTime.of(2019, 5, 10, 8, 0)
        description =
            "Roses Carnations Daisies Roses Carnations Daisies Roses Carnations Daisies Roses Carnations Daisies"
    })
    val Workshop = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Workshop"
        businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2020, 3, 21, 12, 0)
        fundSize = 6500
        endDate = LocalDateTime.of(2021, 12, 20, 8, 0)
        description =
            "Electronics repair of any complexity Electronics repair of any complexity Electronics repair of any complexity Electronics repair of any complexity"
    })
    val OnlineStore = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Online store"
        businessman = findB.fetch(Andey)!!
        startDate = LocalDateTime.of(2019, 1, 21, 12, 0)
        fundSize = 3000
        endDate = LocalDateTime.of(2021, 8, 20, 8, 0)
        description = "Thousands of products for every taste"
    })
    val ClothingStore = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Clothing store"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2021, 1, 21, 12, 0)
        fundSize = 2340
        endDate = LocalDateTime.of(2021, 10, 20, 0, 0)
        description = "Fashionable clothes Fashionable clothes Fashionable clothes"
    })
    val СureCancer = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "The cure for cancer"
        businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2021, 1, 21, 12, 0)
        fundSize = 2340
        endDate = LocalDateTime.of(2023, 10, 20, 0, 0)
        description = "The cure for cancer The cure for cancer"
    })
    val NursingHome = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Nursing Home"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2019, 1, 2, 12, 0)
        fundSize = 3400
        endDate = LocalDateTime.of(2023, 10, 10, 0, 0)
        description = "Nursing Home Nursing Home"
    })
    val CandyFactory = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Candy Factory"
        businessman = findB.fetch(Sonya)!!
        startDate = LocalDateTime.of(2022, 1, 2, 12, 0)
        fundSize = 4000
        endDate = LocalDateTime.of(2024, 10, 10, 0, 0)
        description = "The most delicious candies The most delicious candies The most delicious candies"
    })
    val Airline = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Air company"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2015, 1, 2, 12, 0)
        fundSize = 80000
        endDate = LocalDateTime.of(2023, 10, 10, 0, 0)
        description = "The cheapest and most comfortable air travel The cheapest and most comfortable air travel"
    })
    val Test = addOper.add(Project {
        creationDate = LocalDateTime.now()
        name = "Test"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2016, 1, 2, 12, 0)
        fundSize = 5000
        endDate = LocalDateTime.of(2022, 10, 10, 0, 0)
        description = "Test Test Test Test Test Test"
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 1, 31, 12, 14)
        project = findP.fetch(CarCompany as Int)!!
        invName = "Sergey"
        feedback = "asd@mail.ru"
        amount = 1000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 31, 12, 14)
        project = findP.fetch(CarCompany as Int)!!
        invName = "Valery"
        amount = 900
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 2, 12, 14)
        project = findP.fetch(CarCompany as Int)!!
        invName = "Pasha"
        amount = 8000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 10, 20, 12, 14)
        project = findP.fetch(CarCompany as Int)!!
        amount = 10000
        feedback = "test@mail.ru"
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 1, 5, 12, 14)
        project = findP.fetch(FlowerShop as Int)!!
        invName = "Dmitry"
        amount = 500
        feedback = "test@mail.ru"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 1, 10, 12, 14)
        project = findP.fetch(FlowerShop as Int)!!
        invName = "Dmitry"
        amount = 500
        feedback = "test@mail.ru"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 2, 10, 12, 14)
        project = findP.fetch(FlowerShop as Int)!!
        amount = 800
        feedback = "test@mail.ru"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 10, 12, 14)
        project = findP.fetch(FlowerShop as Int)!!
        invName = "Pasha"
        amount = 100
    })


    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 6, 10, 12, 14)
        project = findP.fetch(OnlineStore as Int)!!
        invName = "Pasha"
        amount = 1000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 8, 10, 12, 14)
        project = findP.fetch(OnlineStore as Int)!!
        invName = "Pasha"
        amount = 2000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 8, 10, 12, 14)
        project = findP.fetch(OnlineStore as Int)!!
        invName = "Sergey"
        amount = 3000
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2021, 2, 10, 12, 14)
        project = findP.fetch(ClothingStore as Int)!!
        invName = "Sergey"
        amount = 100
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2021, 3, 10, 12, 14)
        project = findP.fetch(ClothingStore as Int)!!
        invName = "Sergey"
        amount = 100
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 2, 10, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 2000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 2, 10, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 500
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 2, 10, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 100
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 2, 10, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 200
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 2, 15, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 2000
        invName = "Sasha"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 2, 18, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 1000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 3, 18, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 100
        invName = "Alina"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2015, 3, 1, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 100
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2016, 3, 1, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 2000
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2018, 2, 5, 12, 14)
        project = findP.fetch(Airline as Int)!!
        amount = 5000
        invName = "Anton"
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2022, 2, 5, 12, 14)
        project = findP.fetch(СureCancer as Int)!!
        amount = 100000
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 10, 1, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 500
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 10, 2, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 800
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 10, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 900
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 11, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 9000
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 1, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 9000
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 2, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 9000

    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 3, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 9000

    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 4, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 9000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 5, 3, 12, 14)
        project = findP.fetch(NursingHome as Int)!!
        amount = 9999
    })
}