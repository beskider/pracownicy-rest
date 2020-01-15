# Aplikacja Pracownicy REST

Aplikacja umożliwia zarządzanie bazą pracowników. WYstawione endpointy HTTP przy pomocy żądań HTTP 
(metody GET, POST, PUT oraz DELETE) umożliwiają manipulowanie danymi (przeglądanie, tworzenie, modyfikację i usuwanie) 
pracowników z bazy.

Strona główna aplikacji http://localhost:8080/

Dokumentacja interfejsu API pod adresem http://localhost:8080/swagger-ui.html

## Użyte technologie

* [Java 8](https://www.java.com/)
* [Spring Boot 2](https://spring.io/)
* [Maven](https://maven.apache.org/)
* [Lombok](https://projectlombok.org/)
* [Swagger](https://swagger.io/)

- Java 8 www.java.com
- Spring Boot 2 www.spring.io
- Maven www.maven.apache.org
- Lombok www.projectlombok.org
- Swagger www.swagger.io


## Uruchamianie

```
	$ ./mvnw spring-boot:run
```

lub

```
	$ ./mvnw clean package	
```

```
	$ ./java -jar target/pracownicy-1.0.0.jar	
```