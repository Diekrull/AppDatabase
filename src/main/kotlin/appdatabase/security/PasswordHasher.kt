package appdatabase.security

import org.mindrot.jbcrypt.BCrypt

// Wrapper de BCrypt para mantener hashing/verificacion en un solo lugar.
object PasswordHasher {

    fun hashPassword(password: String): String {
        // Genera un hash con salt automatico.
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verifyPassword(password: String, hashedPassword: String): Boolean {
        // BCrypt compara password plana con hash almacenado.
        return BCrypt.checkpw(password, hashedPassword)
    }
}
