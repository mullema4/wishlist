# Darth Vader's wishlist

Java/JUnit demo app illustrating usage of various Spring, JPA and Hibernate features:

* **@EntityGraph** - EAGER loading of selected attributes ([ClientRepository](src/main/java/dk/cngroup/wishlist/entity/ClientRepository.java))
* **JPA auditing** - automatic insertion of useful entity stuff ([AuditableEntity](src/main/java/dk/cngroup/wishlist/entity/AuditableEntity.java))
* **@PrePersist** - adds new behavior to Entity before save happens ([AuditableEntity](src/main/java/dk/cngroup/wishlist/entity/AuditableEntity.java))
* **@SQLRestriction** - allows soft deletes and other permanent filtering of entities ([Client](src/main/java/dk/cngroup/wishlist/entity/Client.java))
* **@Formula** - Hibernate computes virtual read-only column value using given expression ([Client](src/main/java/dk/cngroup/wishlist/entity/Client.java))
* **@OrderColumn** - allows preserving collection order even after save/load ([Wishlist](src/main/java/dk/cngroup/wishlist/entity/Wishlist.java))
* **Spring WebMVC controllers** - explicit CRUD endpoints for clients, products and wishlists ([controller package](src/main/java/dk/cngroup/wishlist/controller))

Project uses **H2 database** (can be [switched to dedicated MySQL](src/main/resources/application.yml)) initialized with [sample data](src/main/java/dk/cngroup/wishlist/DatabaseInitializer.java).

Useful runtime URLs:
* **[Swagger UI](http://localhost:8080/openapi/swagger)**
* **[REST API](http://localhost:8080/clients)**
