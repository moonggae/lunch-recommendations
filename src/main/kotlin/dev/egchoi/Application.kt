package dev.egchoi

import dev.egchoi.service.MenuService
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val db = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver"
    )

    configureRouting(MenuService(db))
}
