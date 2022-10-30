package ru.ac.uniyar

import ru.ac.uniyar.domain.database.H2DatabaseManager
import ru.ac.uniyar.domain.database.performMigrations
import ru.ac.uniyar.web.startApplication

fun main() {
//    val h2databaseManager = H2DatabaseManager().initialize()
//    println("Веб-интерфейс базы данных доступен по адресу http://localhost:${H2DatabaseManager.WEB_PORT}")
//    println("Введите любую строку, чтобы завершить работу приложения")
//
//    performMigrations()
//
////    val names = db.useConnection { conn ->
////        val sql = """drop all objects delete files"""
////    }
////    val db = connectToDatabase()
////
////    db.from(ProjectTable).select(ProjectTable.name).forEach { row ->
////        println(row[ProjectTable.name])
////    }
//
//
//
//
//    readlnOrNull()
//    h2databaseManager.stopServers()


    //loadData()
    startApplication()
    //saveData()

}
