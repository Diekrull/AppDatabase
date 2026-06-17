package appdatabase.service

import appdatabase.config.JwtConfig
import appdatabase.domain.model.User

// Abstraccion para emitir tokens sin acoplar AuthService a JwtConfig.
interface TokenService {
    fun generateToken(user: User): String
}

class JwtTokenService : TokenService {
    override fun generateToken(user: User): String =
        JwtConfig.generateToken(
            user.id.toString(),
            user.role
        )
}
