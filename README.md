# Darth Vader's wishlist

<p>Demo app illustrating usage of various Spring, JPA and Hibernate features:<p/>

* EntityGraph - EAGER loading of selected attributes ([ClientRepository](src/main/java/dk/cngroup/fetch/repository/ClientRepository.java))
* JPA auditing - automatic insertion of usefull entity stuff ([AuditableEntity](src/main/java/dk/cngroup/fetch/entity/AuditableEntity.java))
* OrderColumn

UPDATE: Added Spring Data REST + HAL Browser. Can be accessed at:
* http://localhost:8080/profile (ALPS descriptors)
* http://localhost:8080 (HAL Browser)
* http://localhost:8080/products (HAL access to Product repository)

