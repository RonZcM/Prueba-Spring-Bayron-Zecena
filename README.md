
---

## SOFTWARE DEVELOPER JAVA SPRING TEST V2 - Gestión de Empleados y Turnos - Bayron Ronaldo Zeceña Moran

Sistema Full Stack desarrollado para la administración de personal y control estricto de horarios. La aplicación garantiza la integridad de los datos mediante validaciones avanzadas, cuenta con una interfaz de usuario dinámica y permite la exportación de reportes operativos.

## Tecnologías Utilizadas

* **Backend:** Java 25, Spring Boot, Spring Data JPA e Hibernate.
* **Frontend:** HTML5, Thymeleaf, Bootstrap 5 y JavaScript Vanilla.
* **Base de Datos:** Microsoft SQL Server Express.
* **Herramientas Adicionales:** SweetAlert2 (UX/UI), jsPDF y SheetJS (Exportación de documentos).

## Lógica de Validación de Solapamiento

El sistema previene la asignación de turnos cruzados para un mismo empleado procesando la validación directamente en la capa de persistencia mediante una consulta JPQL optimizada. El algoritmo matemático evalúa:

* El inicio del nuevo turno es estrictamente menor al fin del turno ya guardado.
* El fin del nuevo turno es estrictamente mayor al inicio del turno ya guardado.
* Si ambas condiciones resultan verdaderas simultáneamente, el sistema detecta el solapamiento, aborta la transacción y notifica al usuario, asegurando una malla horaria sin conflictos.

## Instrucciones de Ejecución

* Clonar este repositorio en el entorno de desarrollo local.
* Configurar Microsoft SQL Server con autenticación mixta y habilitar el protocolo TCP/IP apuntando al puerto fijo 1433.
* Crear una base de datos vacía nombrada `texops_db`.
* Actualizar las credenciales de conexión (`spring.datasource.username` y `password`) dentro del archivo `application.properties`.
* Ejecutar la clase principal `AppApplication.java` utilizando un IDE moderno. (ej. intelliJ IDEA 2025.2.2)
* Ingresar desde cualquier navegador web a la ruta `http://localhost:8080`.

---
