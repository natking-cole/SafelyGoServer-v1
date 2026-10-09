package com.example


import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, World! This API works!")
        }

        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/users") {
            val users: List<User> = withContext(Dispatchers.IO) {
                Database.connect().use { connection ->
                    connection.prepareStatement(
                        "SELECT id, username FROM dbo.users"
                    ).use { statement ->
                        statement.executeQuery().use { result ->
                            val rows = mutableListOf<User>()

                            while (result.next()) {
                                rows.add(
                                    User(
                                        id = result.getInt("id"),
                                        username = result.getString("username")
                                    )
                                )
                            }

                            rows
                        }
                    }
                }
            }

            call.respond(users)
        }
    }
}