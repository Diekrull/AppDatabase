package appdatabase.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    // Configuracion minima del token usado por el backend.
    private const val secret = "super_secret_key"
    private const val issuer = "fitness-api"
    private const val audience = "fitness-users"
    const val realm = "fitness-api"

    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(userId: String, role: String): String {
        // El token guarda solo datos necesarios para identificar al usuario.
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withClaim("role", role)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .sign(algorithm)
    }

    fun getVerifier() = JWT
        // Verificador usado por Ktor Authentication para aceptar/rechazar tokens.
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()
}
