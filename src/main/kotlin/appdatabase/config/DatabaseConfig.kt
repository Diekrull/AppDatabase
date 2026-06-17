package appdatabase.config

import org.jetbrains.exposed.sql.Database

fun configureDatabase() {
    // Conexion directa a PostgreSQL. Las credenciales vienen desde Android Studio o el entorno.
    val dbUrl =
        env("DB_URL")
            ?: error("Falta configurar DB_URL")

    val dbUser =
        env("DB_USER", "DB_user")
            ?: error("Falta configurar DB_USER o DB_user")

    val dbPassword =
        env("DB_PASSWORD", "DB_password")
            ?: error("Falta configurar DB_PASSWORD o DB_password")

    Database.connect(
        url = normalizePostgresUrl(dbUrl),
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )
}

private fun env(vararg names: String): String? =
    names.firstNotNullOfOrNull { name ->
        System.getenv(name)?.takeIf { it.isNotBlank() }
    }

private fun normalizePostgresUrl(dbUrl: String): String {
    // Neon puede entregar URL postgres/postgresql; Exposed necesita jdbc:postgresql.
    val jdbcUrl =
        when {
            dbUrl.startsWith("jdbc:postgresql:") -> dbUrl
            dbUrl.startsWith("postgresql://") -> "jdbc:$dbUrl"
            dbUrl.startsWith("postgres://") -> "jdbc:postgresql://${dbUrl.removePrefix("postgres://")}"
            else -> dbUrl
        }

    if (!jdbcUrl.contains("neon.tech") || jdbcUrl.contains("sslmode=", ignoreCase = true)) {
        return jdbcUrl
    }

    val separator =
        if (jdbcUrl.contains("?")) "&" else "?"

    return "$jdbcUrl${separator}sslmode=require"
}
