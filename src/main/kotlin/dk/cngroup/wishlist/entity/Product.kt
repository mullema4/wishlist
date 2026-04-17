package dk.cngroup.wishlist.entity

import jakarta.persistence.Entity
import jakarta.validation.constraints.NotNull
import org.springframework.data.jpa.repository.JpaRepository

@Entity
class Product(
    @field:NotNull
    var code: String
) : AuditableEntity()

interface ProductRepository : JpaRepository<Product, Long>
