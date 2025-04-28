# Operación Fuego de Quásar

Solución al Challenge Operación Fuego de Quásar de evaluación para desarrolladores backend.

## Requisitos

* Maven
* Java 21
* Web Browser
* Terminal

## Estructura de proyecto

Patron MVC(Model - View - Controller)
* controllers: atienden las peticiones HTTP.
* services: lógica de negocio.
* repositories: acceso a datos.
* config: configuriones customizadas.
* exception: excepciones customizadas.

## Instalación y ejecución

1. clonar el repositorio <remote>
2. Posicionarse en la carpeta del proyecto clonado y ejecutar el siguiente comando en la terminal: mvn clean compile install
3. Dentro de la carpeta del proyecto, ejecutar el siguiente comando en la terminal: java -jar target/challenge-1.0.0.jar.
4. Aplicacion iniciada.
5. Ingresar a http://localhost:8080/swagger-ui.html en el browser.
