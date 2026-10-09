package com.example

import java.sql.Connection
import java.sql.DriverManager

object Database {
    fun connect(): Connection {
        val url =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=SafelyGo;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true"

        val username = "safelygo_user"
        val password = "password123"
        return DriverManager.getConnection(
            url,
            username,
            password
        )
    }
}