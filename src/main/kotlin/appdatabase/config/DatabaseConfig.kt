package appdatabase.config

import org.jetbrains.exposed.sql.Database


fun configureDatabase() {
    val dbUrl = System.getenv("DB_URL")
    val dbUser = System.getenv("DB_USER")
    val dbPassword = System.getenv("DB_PASSWORD")

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )
}