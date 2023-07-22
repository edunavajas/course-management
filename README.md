# Course Management API

API de gestión de cursos. Proyecto implementado usando Java 8 con Spring Boot y Gradle.


## Descripción del Proyecto

Este proyecto sirve como una muestra de mis habilidades como desarrollador Java. Se trata de una API para la gestión de una escuela, donde se pueden administrar cursos y estudiantes, así como las inscripciones de estos estudiantes en los cursos.

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

Si prefieres ejecutar la aplicación en local, sigue los siguientes pasos:

1. Asegúrate de que la base de datos está corriendo. Si usas Docker para esto, ejecuta el siguiente comando:

```
docker run --name my-postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=course_management -p 5432:5432 -d postgres
```

PD: Si docker te da algún problema con ese puerto puedes probar a cambiarlo

```
docker run --name my-postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=course_management -p 5433:5432 -d postgres
```

Si lo cambias ten en cuenta que en el application properties la conexión tiene que cambiarse por el puerto 5433 (en este caso)

```
spring.datasource.url=jdbc:postgresql://localhost:5433/course_management
```

2. A continuación, en la raíz del proyecto, compila el código fuente con:

```
gradle build
```

3. Una vez compilado, abre el proyecto en tu IDE de confianza y ejecuta la clase `CourseManagementApplication`.

## Creación de la Base de Datos

La creación de la base de datos e inserción de todos sus datos mockup se manejan automáticamente mediante **Liquibase**.  La base de datos relacional utilizada es **PostgreSQL** y se utiliza **JPA** para interactuar con esta.

## Documentación de la API

Ya que el diseño de la Api se ha hecho utilizando **API First con Swagger** la documentación se encuentra en el archivo `resources/openapi/course-management-api.yml`. Recomiendo importar
este archivo en Postman para facilitar las pruebas de los endpoints.

## Usuarios por defecto

El micro dispone de seguridad por JWT, ya que he utilizado **Spring Security**, por eso he creado dos usuarios por defecto:

1. Usuario: `user`
   Contraseña: `user`

2. Usuario: `admin`
   Contraseña: `admin`

## Validaciones

Las validaciones las he implementado a nivel de entidad y mediante una clase de validaciones específica usando `@InitBinder`. Esto garantiza
que se te avise si un campo no es correcto al hacer cualquier petición.

## Credenciales de la Base de Datos

Las credenciales de la base de datos se encuentran en el fichero `application.properties`.

## Nota

En los DTOs, a pesar de usar **Lombok**, tuve que añadir manualmente los getters debido a un conflicto con los mappers.

## Autor

Este proyecto ha sido creado y desarrollado por Eduardo Navajas Cortés.
