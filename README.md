Puedes agregar una sección de "Autor" o "Creado por" al final del archivo README. Aquí está tu README actualizado:

---

# Course Management API

API de gestión de cursos. Este proyecto ha sido implementado usando Java 8 con Spring Boot y Gradle.

## Prerrequisitos

- Java 8
- Docker
- Docker Compose
- Postgres

## Instrucciones para la ejecución con Docker

Para ejecutar la aplicación usando Docker, simplemente debes ejecutar el siguiente comando en la raíz del proyecto:

```
docker-compose up
```

Por favor, verifica que tienes instalado Docker y Docker Compose correctamente antes de intentar este paso. Si
experimentas algún problema, asegúrate de tener instalado Postgres en tu Docker.

## Ejecución en local

Si prefieres ejecutar la aplicación en local, primero debes iniciar la base de datos con el siguiente comando:

```
docker run --name my-postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=course_management -p 5432:5432 -d postgres
```

## Creación de la Base de Datos

La creación de la base de datos e inserción de todos sus datos mockup se manejan automáticamente mediante Liquibase.

## Documentación de la API

La documentación de la API se encuentra en el archivo `resources/openapi/course-management-api.yml`. Recomiendo importar
este archivo en Postman para facilitar las pruebas de los endpoints.

## Usuarios por defecto

He creado dos usuarios por defecto:

1. Usuario: `user`
   Contraseña: `user`

2. Usuario: `admin`
   Contraseña: `admin`

## Validaciones

Las validaciones las he implementado a nivel de entidad y mediante una clase de validaciones específica. Esto garantiza
que se te avise si un campo no es correcto.

## Credenciales de la Base de Datos

Las credenciales de la base de datos se encuentran en el archivo `application.properties`.

## Nota

En los DTOs, a pesar de usar Lombok, tuve que añadir manualmente los getters debido a un conflicto con los mappers.

## Autor

Este proyecto ha sido creado y desarrollado por Eduardo Navajas Cortés.

## Contacto

Por favor, si tienes alguna duda acerca de la aplicación, no dudes en contactarme. Gracias.

---
