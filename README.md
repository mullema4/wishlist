# Darth Vader's wishlist

Kotlin/[Spock](https://spockframework.org/spock/docs/) demo app illustrating usage of various Spring, JPA and Hibernate features:

* **@EntityGraph** - EAGER loading of selected attributes ([ClientRepository](src/main/kotlin/dk/cngroup/wishlist/entity/Client.kt))
* **JPA auditing** - automatic insertion of useful entity stuff ([AuditableEntity](src/main/kotlin/dk/cngroup/wishlist/entity/AuditableEntity.kt))
* **@PrePersist** - adds new behavior to Entity before save happens ([AuditableEntity](src/main/kotlin/dk/cngroup/wishlist/entity/AuditableEntity.kt))
* **@SQLRestriction** - allows soft deletes and other permanent filtering of entities ([Client](src/main/kotlin/dk/cngroup/wishlist/entity/Client.kt))
* **@Formula** - Hibernate computes virtual read-only column value using given expression ([Client](src/main/kotlin/dk/cngroup/wishlist/entity/Client.kt))
* **@OrderColumn** - allows preserving collection order even after save/load ([Wishlist](src/main/kotlin/dk/cngroup/wishlist/entity/Wishlist.kt))
* **Spring WebMVC controllers** - explicit CRUD endpoints for clients, products and wishlists ([controller package](src/main/kotlin/dk/cngroup/wishlist/controller))

Project uses **H2 database** (can be [switched to dedicated MySQL](src/main/resources/application.yml)) initialized with [sample data](src/main/kotlin/dk/cngroup/wishlist/DatabaseInitializer.kt).

Useful runtime URLs:
* **[Swagger UI](http://localhost:8080/openapi/swagger)**
* **[REST API](http://localhost:8080/clients)**

