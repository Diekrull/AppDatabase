package appdatabase.service

// Error de negocio para credenciales invalidas o acceso no autorizado.
class UnauthorizedException(message: String) : RuntimeException(message)
