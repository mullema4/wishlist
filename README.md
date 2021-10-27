# Darth Vader's wishlist

<p>Demo app illustrating usage of various Spring, JPA and Hibernate features:</p>

* **@EntityGraph** - EAGER loading of selected attributes ([ClientRepository](src/main/java/dk/cngroup/fetch/repository/ClientRepository.java))
* **JPA auditing** - automatic insertion of useful entity stuff ([AuditableEntity](src/main/java/dk/cngroup/fetch/entity/AuditableEntity.java))
* **@PrePersist** - adds new behavior to Entity before save happens ([AuditableEntity](src/main/java/dk/cngroup/fetch/entity/AuditableEntity.java))
* **@Where** - allows soft deletes and other permanent filtering of entities ([Client](src/main/java/dk/cngroup/fetch/entity/Client.java))
* **@Formula** - Hibernate computes virtual read-only column value using given expression ([Client](src/main/java/dk/cngroup/fetch/entity/Client.java))
* **@OrderColumn** - allows preserving collection order even after save/load ([Wishlist](src/main/java/dk/cngroup/fetch/entity/Wishlist.java))
* **Spring Data REST** - automatic exposure of Spring Data repositories via REST API ([spring-boot-starter-data-rest](build.gradle) dependency)
* **@RepositoryRestController** - enhances Spring Data REST API by custom behavior ([ClientController](src/main/java/dk/cngroup/fetch/controller/ClientController.java))

Project uses **H2 database** (can be [switched to dedicated MySQL](src/main/resources/application.properties)) initialized with [sample data](src/main/java/dk/cngroup/fetch/DatabaseInitializer.java).

Useful runtime URLs:
* **[Swagger UI](http://localhost:8080/openapi/swagger)**
* **[Spring Data REST API](http://localhost:8080)**
* [Spring Data ALPS descriptors](http://localhost:8080/profile)

