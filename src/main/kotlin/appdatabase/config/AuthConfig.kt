package appdatabase.config

import appdatabase.data.dto.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

fun Application.configureAuthentication() {
    // Valida tokens JWT emitidos por JwtConfig para rutas protegidas.
    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtConfig.realm

            verifier(JwtConfig.getVerifier())

            validate { credential ->
                val userId = credential.payload
                    .getClaim("userId")
                    .asString()

                val role = credential.payload
                    .getClaim("role")
                    .asString()

                if (!userId.isNullOrBlank() && !role.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ErrorResponse(error = "Token invalido o expirado")
                )
            }
        }
    }
}
