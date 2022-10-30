package ru.ac.uniyar.domain.database

import org.h2.tools.Server
import java.sql.DriverManager
import kotlin.concurrent.thread

class H2DatabaseManager {
    private var tcpServer: Server? = null
    private var webServer: Server? = null
    private val shutdownThread = thread(start = false, name = "") {
        println("Stopping server")
        stopServers()
    }

    companion object {
        const val JDBC_CONNECTION = "jdbc:h2:tcp://localhost/database.h2"
        const val WEB_PORT = 8082
    }

    fun initialize(): H2DatabaseManager {
        startServers()
        registerShutdownHook()
        return this
    }

    private fun startServers() {
        tcpServer = Server.createTcpServer(
            "-tcpPort", "9092",
            "-baseDir", ".",
            "-ifNotExists",
        ).start()
        webServer = Server.createWebServer(
            "-webPort", WEB_PORT.toString(),
            "-baseDir", ".",
            "-ifNotExists",
        ).start()
    }

    fun dropAll() {
        val con = DriverManager.getConnection("jdbc:h2:tcp://localhost/database.h2", "sa", "")
        con.createStatement()
            .execute("drop all objects delete files")
        con.close()
    }

    private fun registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(shutdownThread)
    }

    fun stopServers() {
        tcpServer?.stop()
        tcpServer = null
        webServer?.stop()
        webServer = null
    }
}
