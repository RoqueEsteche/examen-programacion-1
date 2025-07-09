📝 Descripción del Proyecto
Este proyecto es una aplicación web básica desarrollada con Spring Boot, Spring Data JPA, Spring Security y Thymeleaf. Su propósito es gestionar una lista de productos, permitiendo operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y demostrando la integración con una base de datos PostgreSQL.

Incluye:

Gestión de Productos: Funcionalidades para añadir, ver, editar y eliminar productos.

Integración con PostgreSQL: Persistencia de datos mediante Spring Data JPA.

Seguridad Básica: Configuración inicial con Spring Security (aunque puedes expandirla según tus necesidades de examen).

Interfaz de Usuario: Renderización de vistas con Thymeleaf para una experiencia web.

🚀 Tecnologías Utilizadas
Java 21

Spring Boot 3.x

Spring Data JPA

Spring Security

Thymeleaf

PostgreSQL (como base de datos)

Maven (para la gestión de dependencias)

Lombok (para reducir código boilerplate)

🛠️ Requisitos Previos
Antes de ejecutar la aplicación, asegúrate de tener instalado lo siguiente:

Java Development Kit (JDK) 21 o superior.

Maven 3.x o superior.

PostgreSQL: Un servidor de base de datos PostgreSQL en funcionamiento.

pgAdmin (opcional, pero recomendado para visualizar la base de datos).

Git

⚙️ Configuración y Ejecución Local
Sigue estos pasos para poner la aplicación en marcha en tu máquina local:

1. Clonar el Repositorio
Abre tu terminal (o Git Bash) y clona el proyecto desde GitHub:

Bash

git clone https://github.com/RoqueEsteche/examen-programacion-1.git
cd examen-programacion-1
2. Configurar la Base de Datos PostgreSQL
Crea una nueva base de datos en tu servidor PostgreSQL (puedes llamarla examen_programacion_db o cualquier otro nombre).

Puedes usar pgAdmin, o ejecutar un comando SQL como:

SQL

CREATE DATABASE examen_programacion_db;
Actualiza application.properties:

Abre el archivo src/main/resources/application.properties.

Modifica las propiedades de conexión con los detalles de tu base de datos PostgreSQL local (nombre de la base de datos, usuario y contraseña).

Properties

# Configuración de la base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_de_tu_base_de_datos
spring.datasource.username=tu_usuario_de_postgres
spring.datasource.password=tu_contraseña_de_postgres

# DDL-auto para que Hibernate cree/actualice las tablas automáticamente
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3. Ejecutar la Aplicación
Puedes ejecutar la aplicación desde tu IDE o desde la terminal:

a) Desde un IDE (IntelliJ IDEA, VS Code)
Abre el proyecto examen-programacion-1 en tu IDE.

Navega a la clase principal: src/main/java/com/example/examen_programacion_1/ExamenProgramacion1Application.java.

Ejecuta el método main de esta clase.

b) Desde la Terminal (con Maven)
Asegúrate de estar en la carpeta raíz del proyecto (examen-programacion-1).

Ejecuta el siguiente comando Maven:

Bash

mvn spring-boot:run
4. Acceder a la Aplicación
Una vez que la aplicación se inicie (verás mensajes en la consola indicando que Tomcat se inició en el puerto 8080), abre tu navegador web y ve a:

http://localhost:8080
La aplicación te redirigirá automáticamente a la lista de productos.

🔍 Verificación de Datos en pgAdmin
Para confirmar que la aplicación está interactuando correctamente con la base de datos:

Abre pgAdmin y conéctate a tu servidor PostgreSQL.

Navega a la base de datos que configuraste para el proyecto (por ejemplo, examen_programacion_db).

Expande Schemas -> public -> Tables.

Deberías ver las tablas generadas por Hibernate (como productos, categorias, etc.).

Haz clic derecho en una tabla (ej. productos) y selecciona View/Edit Data -> All Rows para ver los datos. Si tu aplicación tiene algún CommandLineRunner para cargar datos iniciales, los verás aquí.

Para una prueba completa, añade un nuevo producto a través de la interfaz web de tu aplicación. Luego, refresca la vista de la tabla productos en pgAdmin; deberías ver el nuevo registro allí, confirmando la persistencia de datos.

🤝 Contribución
Si alguien quisiera contribuir (aunque para un proyecto de examen quizás no sea el foco principal):

Haz un "fork" de este repositorio.

Crea una nueva rama (git checkout -b feature/nombre-de-tu-caracteristica).

Realiza tus cambios y haz "commit" (git commit -m 'feat: añade nueva característica').

Sube tus cambios a tu "fork" (git push origin feature/nombre-de-tu-caracteristica).

Abre un "Pull Request" a este repositorio.
