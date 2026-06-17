package appdatabase.service

// Error de negocio para conflictos de estado, por ejemplo registros duplicados.
class ConflictException(message: String) : RuntimeException(message)
