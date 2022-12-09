package ru.ac.uniyar.domain.database

import ru.ac.uniyar.domain.entities.Businessman
import ru.ac.uniyar.domain.entities.Project
import ru.ac.uniyar.domain.entities.Investment
import ru.ac.uniyar.domain.operations.OperationHolder
import java.time.LocalDateTime

fun fill(opHolder: OperationHolder) {
    val addProject = opHolder.addProjectOperation
    val addBusinessman = opHolder.addBusinessman
    val findB = opHolder.businessmanFetchOperation
    val invest = opHolder.addProjectInvestOperation
    val findP = opHolder.fetchProjectOperation

    val Ivan = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Ivan"
    })
    val Sergey = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Sergey"
    })
    val Andey = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Andey"
    })
    val Anton = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Anton"
    })
    val Nastya = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Nastya"
    })
    val Alina = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Alina"
    })
    val Sonya = addBusinessman.add(Businessman {
        creationDate = LocalDateTime.now()
        name = "Sonya"
    })

    val CarCompany = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Car company"
        businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2018, 5, 31, 12, 14)
        fundSize = 19000
        endDate = LocalDateTime.of(2020, 2, 20, 8, 0)
        description = "Car sales Car sales Car sales Car sales Car sales Car sales"
    })
    val FlowerShop = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Flower Shop"
        businessman = findB.fetch(Sergey)!!
        startDate = LocalDateTime.of(2019, 1, 31, 12, 14)
        fundSize = 5000
        endDate = LocalDateTime.of(2019, 8, 10, 8, 0)
        description =
            "Roses Carnations Daisies Roses Carnations Daisies Roses Carnations Daisies Roses Carnations Daisies"
    })
    val Workshop = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Workshop"
        businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2020, 3, 21, 12, 0)
        fundSize = 6500
        endDate = LocalDateTime.of(2021, 6, 20, 8, 0)
        description =
            "Electronics repair of any complexity Electronics repair of any complexity Electronics repair of any complexity Electronics repair of any complexity"
    })
    val OnlineStore = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Online store"
        businessman = findB.fetch(Andey)!!
        startDate = LocalDateTime.of(2019, 3, 21, 12, 0)
        fundSize = 3000
        endDate = LocalDateTime.of(2021, 8, 20, 8, 0)
        description = "Thousands of products for every taste"
    })
    val ClothingStore = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Clothing store"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2021, 1, 21, 12, 0)
        fundSize = 2340
        endDate = LocalDateTime.of(2021, 10, 20, 0, 0)
        description = "Fashionable clothes Fashionable clothes Fashionable clothes"
    })
    val СureCancer = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "The cure for cancer"
        businessman = findB.fetch(Ivan)!!
        startDate = LocalDateTime.of(2021, 1, 21, 12, 0)
        fundSize = 2340
        endDate = LocalDateTime.of(2023, 10, 20, 0, 0)
        description = "The cure for cancer The cure for cancer"
    })
    val NursingHome = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Nursing Home"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2019, 1, 2, 12, 0)
        fundSize = 3400
        endDate = LocalDateTime.of(2023, 10, 10, 0, 0)
        description = "Nursing Home Nursing Home"
    })
    val CandyFactory = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Candy Factory"
        businessman = findB.fetch(Sonya)!!
        startDate = LocalDateTime.of(2022, 1, 2, 12, 0)
        fundSize = 4000
        endDate = LocalDateTime.of(2024, 10, 10, 0, 0)
        description = "The most delicious candies The most delicious candies The most delicious candies"
    })
    val Airline = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Air company"
        businessman = findB.fetch(Anton)!!
        startDate = LocalDateTime.of(2015, 1, 2, 12, 0)
        fundSize = 80000
        endDate = LocalDateTime.of(2023, 10, 10, 0, 0)
        description = "The cheapest and most comfortable air travel The cheapest and most comfortable air travel"
    })
    val RoadRepairs = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Road repairs"
        businessman = findB.fetch(Nastya)!!
        startDate = LocalDateTime.of(2012, 1, 2, 12, 0)
        fundSize = 6000
        endDate = LocalDateTime.of(2016, 10, 10, 0, 0)
        description = "high-quality roads high-quality roads high-quality roads high-quality roads high-quality roads"
    })
    val UpdatingHospitals = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Renovation of hospitals"
        businessman = findB.fetch(Alina)!!
        startDate = LocalDateTime.of(2018, 1, 2, 12, 0)
        fundSize = 40000
        endDate = LocalDateTime.of(2024, 10, 10, 0, 0)
        description = "Hospitals with modern equipment Hospitals with modern equipment"
    })
    val ElectricCars = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Electric cars"
        businessman = findB.fetch(Andey)!!
        startDate = LocalDateTime.of(2019, 1, 2, 12, 0)
        fundSize = 56000
        endDate = LocalDateTime.of(2028, 2, 10, 0, 0)
        description = "Cars without emissions Cars without emissions Cars without emissions"
    })
    val SpaceProgram = addProject.add(Project {
        creationDate = LocalDateTime.now()
        name = "Space program"
        businessman = findB.fetch(Andey)!!
        startDate = LocalDateTime.of(2020, 1, 2, 12, 0)
        fundSize = 900000
        endDate = LocalDateTime.of(2028, 10, 10, 0, 0)
        description = "Space program Space program Space program"
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

    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 2, 1, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 500
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 2, 2, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 800
        invName = "Anton"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 2, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 900
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 9000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 4, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 2000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 5, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 5000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 5, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 5000
        invName = "Anton"
        feedback = "xasdf@mail.eu"
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 1, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 8000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 2, 6, 12, 14)
        project = findP.fetch(ElectricCars as Int)!!
        amount = 500
    })

    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 1, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 500
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 2, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 500
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 3, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 500
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 4, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 1200
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 5, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 900
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 6, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 800
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2019, 7, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 200
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 1, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 5000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 2, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 3000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 4, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 8000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 6, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 9000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 7, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 10000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 7, 8, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 10000
    })
    invest.add(Investment {
        creationDate = LocalDateTime.of(2020, 8, 6, 12, 14)
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 9200
    })
    invest.add(Investment {
        creationDate = LocalDateTime.now()
        project = findP.fetch(UpdatingHospitals as Int)!!
        amount = 8000
    })
}