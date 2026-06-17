# ProjectAPI

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:
 * [Ktor Documentation](https://ktor.io/docs/home.html)
 * [Ktor GitHub page](https://github.com/ktorio/ktor)
 * [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). [Request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up).


## Features
Here's a list of features included in this project:

| Name | Description |
|------|-------------|

## Building & Running
To build or run the project, use one of the following tasks:


| Task | Description |
|------|-------------|

If the server starts successfully, you'll see the following output:
```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## Flujo del backend

El backend sigue este flujo:

1. `data/database`: define las tablas fisicas con Exposed (`UsersTable`, `ProgramsTable`).
2. `data/repository`: lee y escribe en la base de datos. Aqui se transforman filas SQL en modelos Kotlin.
3. `service`: contiene reglas de negocio, validaciones, hashing de password y generacion de token.
4. `routes`: recibe requests HTTP, convierte JSON a DTOs, llama servicios y responde DTOs.
5. `config/ErrorConfig.kt`: centraliza errores para responder JSON consistente.

Ejemplo de respuesta de error:

```json
{
  "error": "Nombre vacio",
  "detail": null
}
```

Variables de entorno esperadas:

```text
DB_URL=jdbc:postgresql://HOST/DB?sslmode=require
DB_USER=USER
DB_PASSWORD=PASSWORD
```

Tambien se aceptan `DB_user` y `DB_password` si asi estan configuradas en Android Studio.
