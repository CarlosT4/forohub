Foro Hub API
============

API REST desarrollada con Spring Boot, Spring Security y JWT para gestionar un foro en línea.  
Permite la creación, actualización, consulta y eliminación de tópicos, así como autenticación de usuarios con control de acceso.

Características
---------------
- Autenticación JWT: Solo usuarios autenticados pueden interactuar con la API.
- Gestión de Tópicos: CRUD completo con filtros por curso y año.
- Gestión de Usuarios y Perfiles.
- Migraciones con Flyway para manejo de esquema de base de datos.
- Validaciones con jakarta.validation.
- Paginación y ordenamiento para listados.

Requisitos previos
------------------
- Java 17+
- MySQL (configuración en application.properties)
- Gradle (o wrapper incluido)
- Flyway para migraciones automáticas

Configuración
-------------
Editar src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=mySecretKey123456789
jwt.expiration=86400000

Base de datos: Se crean automáticamente las tablas y datos iniciales al iniciar la aplicación gracias a Flyway (src/main/resources/db/migration).

Instalación y ejecución
-----------------------
# Clonar repositorio
git clone https://github.com/CarlosT4/forohub.git
cd forohub

# Compilar y ejecutar
./gradlew bootRun

La API estará disponible en http://localhost:8080.

Endpoints principales
---------------------

Autenticación
-------------
POST /login - Autenticar usuario y obtener token JWT

Ejemplo de request en Postman:
{
  "correoElectronico": "admin@forohub.com",
  "contrasena": "123456"
}

Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}

Agregar en headers de las siguientes peticiones:
Authorization: Bearer <token>

Tópicos
-------
GET    /topicos          - Listar tópicos (con paginación y filtros curso y anio)
GET    /topicos/{id}     - Detalle de un tópico
POST   /topicos          - Crear nuevo tópico
PUT    /topicos/{id}     - Actualizar tópico
DELETE /topicos/{id}     - Eliminar tópico

Dependencias principales
------------------------
- Spring Boot (Web, Data JPA, Security, Validation)
- MySQL Driver
- Flyway
- Lombok
- Auth0 Java JWT
